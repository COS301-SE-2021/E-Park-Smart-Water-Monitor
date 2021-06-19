package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.EditUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.EditUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllUsersResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.LoginResponse;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ParkService parkService;


    @Autowired
    public UserServiceImpl(@Qualifier("UserRepo") UserRepo userRepo,
                           @Qualifier("ParkService") ParkService parkService) {
        this.userRepo = userRepo;
        this.parkService = parkService;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws InvalidRequestException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        CreateUserResponse response = new CreateUserResponse();

        UUID parkId = request.getParkId();
        String idNumber = request.getIdNumber();
        String name = request.getName();
        String surname = request.getSurname();
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        String role = request.getRole();
        String cellNumber = request.getCellNumber();

        if (parkId != null
                && !name.equals("")
                && !surname.equals("")
                && !email.equals("")
                && !password.equals("")) {

            List<User> users = userRepo.findUserByIdNumber(idNumber);
            List<User> usersByUsername = userRepo.findUserByUsername(username);

            if (users.size() < 1) {
                if (usersByUsername.size() < 1) {
                    FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(parkId));

                    Park park = findByParkIdResponse.getPark();

                    User user = new User(Long.parseLong(idNumber), email, name, surname, passwordEncoder.encode(password), username, role, park, cellNumber);

                    userRepo.save(user);

                    response.setStatus("Successfully create user: "
                            + name
                            + " "
                            + surname
                            + " and added them to park: "
                            + park.getParkName());

                    response.setSuccess(true);
                } else {
                    response.setSuccess(false);
                    response.setStatus("A user with this username already exists.");
                }
            } else {
                response.setSuccess(false);
                response.setStatus("A user with this id number already exists.");
            }
        } else {
            response.setSuccess(false);
            response.setStatus("Failed to create user.");
        }
        return response;
    }

    @Override
    public EditUserResponse editUser(EditUserRequest request) {
        EditUserResponse response = new EditUserResponse();
        List<User> usersWithUsername = userRepo.findUserByUsername(request.getUsername());
        User userToChange=null;
        if (usersWithUsername.size() != 0)
        {
            userToChange= usersWithUsername.get(0);

            if (!request.getNewUsername().equals("")) {
                usersWithUsername = userRepo.findUserByUsername(request.getNewUsername());
                if (usersWithUsername.size() == 0) {
                    userToChange.setUsername(request.getNewUsername());
                } else {
                    response.setStatus("Username is already in use.");
                    response.setSuccess(false);
                    return response;

                }
            }
            if (!request.getCellNumber().equals(""))
            {
                Pattern p = Pattern.compile("\\d{10}");
                Matcher m = p.matcher(request.getCellNumber());
                boolean validNumber = m.matches();

                if (validNumber) {
                    userToChange.setCellNumber(request.getCellNumber());

                } else {
                    response.setStatus("Cell-number provided is not valid.");
                    response.setSuccess(false);
                    return response;

                }

            }
            if (!request.getEmail().equals("")) {

                List<User> usersWithThisEmail = userRepo.findUserByEmail(request.getEmail());

                if (usersWithThisEmail.size() == 0) {

                    Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

                    Matcher emailMatcher = emailPattern.matcher(request.getEmail());
                    boolean validEmail = emailMatcher.matches();
                    if (validEmail)
                        userToChange.setEmail(request.getEmail());
                    else {
                        response.setStatus("The provided email is not a valid email-address.");
                        response.setSuccess(false);
                    }

                } else {
                    response.setStatus("The provided email is already in use.");
                    response.setSuccess(false);
                    return  response;

                }

            }
            if (!request.getIdNumber().equals("")) {

                List<User> usersWithThisIdNumber = userRepo.findUserByIdNumber(request.getIdNumber());

                if (usersWithThisIdNumber.size() == 0)
                {
                    Pattern idPattern = Pattern.compile("\\d{13}");

                    Matcher idMatcher = idPattern.matcher(request.getIdNumber());
                    boolean validID = idMatcher.matches();
                    if (validID)
                        userToChange.setIdNumber(Long.parseLong(request.getIdNumber()));
                    else
                        {
                        response.setStatus("The provided ID number is not a valid ID number.");
                        response.setSuccess(false);
                        return  response;

                    }
                }
                else
                {
                    response.setStatus("The provided ID number is already registered to someone else.");
                    response.setSuccess(false);
                    return  response;
                }
            }
            if (!request.getName().equals(""))
            {
                userToChange.setName(request.getName());
            }
            if (!request.getSurname().equals(""))
            {
                userToChange.setSurname(request.getSurname());

            }
            if (!request.getRole().equals(""))
            {
                userToChange.setRole(request.getRole());

            }




            if (userToChange != null)
            {
                userRepo.save(userToChange);
            }



            response.setStatus("User details updated.");
            response.setSuccess(true);

        } else {
            response.setStatus("User with that username does not exist.");
            response.setSuccess(false);
        }

        return response;
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) throws InvalidRequestException {
        String username = request.getUsername();
        String password = request.getPassword();
        if (username.equals("")||password.equals("")){
            throw new InvalidRequestException("Login details not complete");
        }
        String JWTToken = "";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

        assert userRepo != null;
        List<User> users = userRepo.findUserByUsername(username);
        if (users.size() <= 1) {
                User user=null;
            if (users.size()==1){
                user = users.get(0);
            }
            if (user == null || !password.equals(user.getPassword())) {
                if (user == null) {
                    throw new InvalidRequestException("User doesnt exist!");
                }else {
                    throw new InvalidRequestException("Wrong Password");
                }
            }else{
                Map<String, Object> head = new HashMap<>();
                Map<String, Object> claims = new HashMap<>();
                claims.put("UUID", user.getId().toString());

                JWTToken = Jwts.builder()
                        .setHeader(head)
                        .setSubject(username)
                        .setClaims(claims)
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                        .signWith(SignatureAlgorithm.HS512, "secret")
                        .compact();

                return new LoginResponse(true, JWTToken,
                        user.getRole(),
                        user.getEmail(),
                        user.getName(),
                        user.getSurname(),
                        user.getUsername(),
                        user.getCellNumber(),
                        user.getIdNumber(),
                        user.getPark().getId(),
                        user.getPark().getParkName());
            }
        }
        throw new InvalidRequestException("To many users with this username");
        //return new LoginResponse(JWTToken, false);
    }

    @Override
    public User findUserByUserName(String username) {
        List<User> user = userRepo.findUserByUsername(username);
        if (user.size() == 1) {
            return user.get(0);
        } else return null;
    }

    @Override
    public GetAllUsersResponse getAllUsers() {
        return new GetAllUsersResponse(userRepo.findAll());
    }
}
