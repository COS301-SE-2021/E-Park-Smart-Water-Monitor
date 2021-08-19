import { useState } from 'react';

// LOGIN RESPONSE
// {
//     "success": true,
//     "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDSEljaGkyIiwicm9sZXMiOiJGSUVMRF9FTkdJTkVFUiIsImV4cCI6MTYyODc2Mzg1NX0.mBZQjxJuuErfibzV6zRtweppy4XhbLA-V4khXINlk31ZYJlSWExLX4p7lD-9NtBIZYMHd5OIS9KLMSXzODGMjg",
//     "userRole": "FIELD_ENGINEER",
//     "userEmail": "nita.nell92@gmail.com",
//     "name": "Nita",
//     "surname": "test",
//     "username": "CHIchi2",
//     "cellNumber": "0728480427",
//     "userIdNumber": 9871233577123,
//     "parkId": "b026bea2-17a4-4939-bbbb-80916d8cf44e",
//     "parkName": "Ethosha National Park"
// }

export default function useToken() {
    const getToken = () => {
        const tokenString = localStorage.getItem('token');
        const userToken = JSON.parse(tokenString);
        return userToken?.token
    };

    const [token, setToken] = useState(getToken());

    const saveToken = userToken => {
        localStorage.setItem('token', JSON.stringify(userToken));
        setToken(userToken.token);
    };

    return {
        setToken: saveToken,
        token
    }
}