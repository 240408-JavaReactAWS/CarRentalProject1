import axios from "axios";

export const commonFunctions = {
    validateSession: async () => {
        try {
            let res = await axios.get('http://localhost:8080/users/session', {
                withCredentials: true
            });
            //console.log(res);
            if (res.status === 200) {
                console.log("Session is Valid")
                return true;
            } else {
                console.log("Session is Invalid")
                return false;
            }
        } catch (error) {
            //console.log("Session is Invalid")
            return false;
        }
    },
    isAdmin: async () => {
        try {
            let res = await axios.get('http://localhost:8080/users/admin', {
                withCredentials: true
            });
            if (res.status === 200) {
                return true;
            } else {
                return false;
            }
        } catch (error) {
            return false;
        }
    },
    hasCar: async () => {
        try {
            let res = await axios.get('http://localhost:8080/users/hascar', {
                withCredentials: true
            });
            if (res.status === 200) {
                return true;
            } else {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
}