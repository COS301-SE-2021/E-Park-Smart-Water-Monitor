import React, { createContext, useState } from "react";

export const UserContext = createContext();

// This context provider is passed to any component requiring the context
export const UserProvider = ({ children }) => {

    // {
    //     "success": true,
    //     "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDSEljaGkyIiwicm9sZXMiOiJGSUVMRF9FTkdJTkVFUiIsImV4cCI6MTYyOTAyODgyNX0.thFaGhQ0cRdkdZ5x1kLZX0xXU9I-H_BRsNrFtEwSOqIDnp_mJ0ZV-_4KuM2b3m3NIsrWpUe0VgKPDu-tE8rBGQ",
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

    const [token, setToken] = useState("");
    const [userRole, setToken] = useState("");

    return (
        <UserContext.Provider
            value={{
                name,
                token,
                setName,
                setLocation
            }}
        >
            {children}
        </UserContext.Provider>
    );
};