import React, { createContext, useState } from "react";

export const UserContext = createContext();

// This context provider is passed to any component requiring the context
export const UserProvider = ({ children }) => {

    const [token, setToken] = useState("");
    const [role, setRole] = useState("");
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [cellNumber, setCellNumber] = useState("");
    const [IDNumber, setIDNumber] = useState("");
    const [parkID, setParkID] = useState("");
    const [parkName, setParkName] = useState("");
    const [username, setUsername] = useState("");

    const isLogin = () => {
        // let token = sessionStorage.getItem('token')
        return !(token === "")
    }

    const Logout = () => {
        setToken("")
    }


    return (
        <UserContext.Provider
            value={{
                // values
                token,
                role,
                email,
                name,
                surname,
                cellNumber,
                IDNumber,
                parkID,
                parkName,
                username,
                // functions
                setToken,
                setRole,
                setEmail,
                setName,
                setSurname,
                setCellNumber,
                setIDNumber,
                setParkID,
                setParkName,
                setUsername,
                isLogin,
                Logout
            }}
        >
            {children}
        </UserContext.Provider>
    );
};