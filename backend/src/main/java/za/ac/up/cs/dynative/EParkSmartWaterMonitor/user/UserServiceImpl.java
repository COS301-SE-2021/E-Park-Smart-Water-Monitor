package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.CreateUserRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.LoginRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.CreateUserResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.GetAllUsersResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.LoginResponse;

import java.time.LocalDate;
import java.util.*;

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
    public CreateUserResponse createUser(CreateUserRequest request) {

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

                    User user = new User(idNumber, email,name,surname,passwordEncoder.encode(password),username, role, park);

                    userRepo.save(user);

                    response.setStatus("Succesfully create user: "
                            + name
                            + " "
                            + surname
                            + " and added them to park: "
                            + park.getParkName());

                    response.setSuccess(true);
                }
                else {
                    response.setSuccess(false);
                    response.setStatus("A user with this username already exists.");
                }
            }
            else {
                response.setSuccess(false);
                response.setStatus("A user with this id number already exists.");
            }
        }
        else {
            response.setSuccess(false);
            response.setStatus("Failed to create user.");
        }
        return response;
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String JWTToken = "";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

        assert userRepo != null;
        List<User> users = userRepo.findUserByUsername(username);
        if (users.size() <= 1) {
            User user = users.get(0);
            if (user == null || !passwordEncoder.matches(password, user.getPassword())){
                if (user == null){
                    System.out.println("User doesnt exist!");
                }
                else{
                    System.out.println("Wrong Password");
                }

            }
            else {
                Map<String, Object> head = new HashMap<>();
                Map<String, Object> claims = new HashMap<>();
                claims.put("UUID", user.getId().toString());
                JWTToken = Jwts.builder()
                        .setHeader(head)
                        .setSubject(username)
                        .setClaims(claims)
                        .setIssuedAt(new Date())
                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
                        .signWith(SignatureAlgorithm.HS512,"secret")
                        .compact();

                return new LoginResponse(JWTToken);
            }
        }
        return new LoginResponse(JWTToken);
    }

    @Override
    public User findUserByUserName(String username) {
        List<User> user = userRepo.findUserByUsername(username);
        if (user.size() == 1) {
            return user.get(0);
        }
        else return null;
    }

    @Override
    public GetAllUsersResponse getAllUsers() {
        return new GetAllUsersResponse(userRepo.findAll());
    }
}
