package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.ParkService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models.Park;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.requests.FindByParkIdRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.responses.FindByParkIdResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.security.JwtTokenProvider;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.repositories.UserRepo;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;
    private final ParkService parkService;
    private final NotificationService notificationService;

    @Autowired
    public UserServiceImpl(@Qualifier("UserRepo") UserRepo userRepo,
                           @Qualifier("ParkService") ParkService parkService,
                           @Lazy @Qualifier("NotificationServiceImpl") NotificationService notificationService) {
        this.userRepo = userRepo;
        this.parkService = parkService;
        this.notificationService= notificationService;
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
                && !role.equals("")
                && !cellNumber.equals("")
                && !username.equals("")
                && !idNumber.equals("")) {

            //cellphone number check:
            Pattern p = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");;
            Matcher m = p.matcher(cellNumber);
            boolean validNumber = m.matches();
            if (!validNumber) {
                response.setStatus("Cell-number provided is not valid.");
                response.setSuccess(false);
                return response;
            }

            //email address check:
            Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher emailMatcher = emailPattern.matcher(request.getEmail());
            boolean validEmail = emailMatcher.matches();
            if (!validEmail) {
                response.setStatus("The provided email is not a valid email-address.");
                response.setSuccess(false);
                return response;
            }

            //id number check:
            Pattern idPattern = Pattern.compile("\\d{13}");
            Matcher idMatcher = idPattern.matcher(request.getIdNumber());
            boolean validID = idMatcher.matches();
            if (!validID){
                response.setStatus("The provided ID number is not a valid ID number.");
                response.setSuccess(false);
                return  response;
            }


            List<User> users = userRepo.findUserByIdNumber(idNumber);
            List<User> usersByUsername = userRepo.findUserByUsername(username);

            if (users.size() < 1) {
                if (usersByUsername.size() < 1) {
                    FindByParkIdResponse findByParkIdResponse = parkService.findByParkId(new FindByParkIdRequest(parkId));

                    Park park = findByParkIdResponse.getPark();

                    if (park != null) {
                        User user = new User(Long.parseLong(idNumber), email, name, surname, passwordEncoder.encode(password), username, role, park, cellNumber);
//                        void addUser( String surname, String password, String username, String role, UUID parkId , String parkName, String cellNumber);
                        userRepo.addUser( UUID.randomUUID(),user.getIdNumber(),user.getEmail(), user.getName(),user.getName(),user.getSurname(),user.getUsername(),user.getRole(),user.getPark().getId(),user.getPark().getParkName(),user.getCellNumber());
//                        userRepo.save(user);

                        response.setStatus("Successfully create user: "
                                + name
                                + " "
                                + surname
                                + " and added them to park: "
                                + park.getParkName());

                        response.setSuccess(true);
                    }
                    else {
                        response.setSuccess(false);
                        response.setStatus("No park with this id exists.");
                    }
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
            response.setStatus("User's details are incomplete");
        }
        return response;
    }

    @Override
    public EditUserResponse editUser(EditUserRequest request) {
        EditUserResponse response = new EditUserResponse();
        List<User> usersWithUsername = userRepo.findUserByUsername(request.getUsername());
        User userToChange=null;
        String originalUsername=request.getUsername();
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
                Pattern p = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
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
//                userRepo.save(userToChange);
                userRepo.editUser(
                        userToChange.getRole() ,
                        userToChange.getIdNumber() ,
                        userToChange.getName(),
                        userToChange.getSurname(),
                        originalUsername,
                        userToChange.getUsername() ,
                        userToChange.getEmail(),
                        userToChange.getCellNumber());

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
    public LoginResponse loginUser(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (username.equals("")||password.equals("")){
            return new LoginResponse("", false);
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
            if (user == null || !passwordEncoder.matches(password,user.getPassword())) {
                if (user == null) {
                    return new LoginResponse(JWTToken, false);
                }else {
                    return new LoginResponse(JWTToken, false);
                }
            }else{
                Map<String, Object> head = new HashMap<>();
                Map<String, Object> claims = new HashMap<>();
                claims.put("UUID", user.getId().toString());

                Collection<SimpleGrantedAuthority> authorities = Sets.newHashSet();
                authorities.add(new SimpleGrantedAuthority(user.getRole()));

                Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

                JWTToken =  jwtTokenProvider.generateToken(auth);
//                        Jwts.builder()
//                        .setHeader(head)
//                        .setSubject(username)
//                        .setClaims(claims)
//                        .setIssuedAt(new Date())
//                        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
//                        .signWith(SignatureAlgorithm.HS512, "secret")
//                        .compact();

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
        return new LoginResponse(JWTToken, false);
    }

    @Override
    public User findUserByUserName(String username) {
        List<User> user = userRepo.findUserByUsername(username);
        if (user.size() == 1) {
            return user.get(0);
        } else return null;
    }

    @Override
    public ArrayList<User> findUsersRelatedToDevice(String dName) {
       return userRepo.findUsersWorkingAtDevicePark(dName);
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        DeleteUserResponse response = new DeleteUserResponse();

        if (deleteUserRequest.getId() != null) {
            User user = userRepo.findUserById(deleteUserRequest.getId());

            if (user != null) {
                userRepo.delete(user);
                response.setStatus("Sucessfully deleteD user: " + user.getName() + " " + user.getSurname());
                response.setSuccess(true);
            }
            else {
                response.setStatus("Failed to delete user: No user with this id exists!");
                response.setSuccess(false);
            }
        }
        else {
            response.setStatus("Failed to delete user no id specified!");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public FindUserByIdResponse findUserById(FindUserByIdRequest request) {
        FindUserByIdResponse response = new FindUserByIdResponse(false, null);
        if (request.getUserId() != null) {
            User user = userRepo.findSpecificUser(request.getUserId());
            if (user != null) {
                response.setStatus(user);
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    public GetAllUsersResponse getAllUsers() {
        return new GetAllUsersResponse(userRepo.getAllUsers());
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws InvalidRequestException {
        String username = resetPasswordRequest.getUsername();
        List<User> userList=  userRepo.findUserByUsername(username);
        if (userList.size()>0){
            User user= userList.get(0);

            //create a code
            String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
            String numbers = "0123456789";
            String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
            StringBuilder sb = new StringBuilder();
            Random random= new Random();

            int length= 10;
            for (int i= 0; i<length; i++){
                int index= random.nextInt(alphaNumeric.length());
                char randomChar = alphaNumeric.charAt(index);
                sb.append(randomChar);
            }
            String code= sb.toString();
            user.setActivationCode(code);

            //code expiration
            user.setResetPasswordExpiration(LocalDateTime.now().plusHours(2));

            String message = "We received your request to reset your password. The code that follows will be valid for 2 hours. ";

            //send email
            ArrayList<String> to= new ArrayList<>();
            to.add(user.getEmail());
            notificationService.sendMail(new EmailRequest("EPark Smart Water Monitoring System",
                    "Password reset",
                     to,
                    null,
                    null,
                     Topic.PASSWORD_RESET,
                     user.getName(),
                     code,
                     message
                    ));
            userRepo.save(user);
            return new ResetPasswordResponse(code);

        }else{
            return new ResetPasswordResponse("User not found");
        }
    }

    @Override
    public ResetPasswordFinalizeResponse resetPasswordFinalize(ResetPasswordFinalizeRequest resetPasswordFinalizeRequest){
        String username= resetPasswordFinalizeRequest.getUsername();
        String code= resetPasswordFinalizeRequest.getResetCode();
        String password1 = resetPasswordFinalizeRequest.getNewPassword();
        String password2 = resetPasswordFinalizeRequest.getNewPasswordConfirm();

        List<User> userList=  userRepo.findUserByUsername(username);
        if (userList.size()==0){
            return new ResetPasswordFinalizeResponse("User not found", false);
        }

        if (userList.get(0).getResetPasswordExpiration().isAfter(LocalDateTime.now())){
            if (code.equals(userList.get(0).getActivationCode()) && password1.equals(password2)){

                BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
                String passwordNew= passwordEncoder.encode(password1);
                userList.get(0).setPassword(passwordNew);
                userList.get(0).setResetPasswordExpiration(LocalDateTime.now());

                userRepo.save(userList.get(0));
                return new ResetPasswordFinalizeResponse("Password successfully changed", true);
            }
            return new ResetPasswordFinalizeResponse("There seems to be a typo in the code or provided password", false);
        }

        return new ResetPasswordFinalizeResponse("Password reset failed, code expired", false);
    }

}
