import React, {
  createContext,
  useContext,
  useState,
  ReactNode,
  useEffect,
} from "react";
import { loginUser, signupUser } from "../services/api";

export interface SignUpResponse {
  statusCode: string;
  message: string;
}

export interface SignInResponse {
  statusCode: string;
  message: string;
  data: {
    token: string;
  };
}

interface AuthContextType {
  user: string | null;
  login: (username: string, password: string) => Promise<SignInResponse>;
  logout: () => void;
  signup: (
    username: string,
    emailId: string,
    password: string,
    role: string
  ) => Promise<SignUpResponse>;
  fetchToken: () => Promise<string>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<string | null>(null);

  const [token, setToken] = useState(
    () => localStorage.getItem("authToken") || null
  );

  const saveToken = (newToken) => {
    localStorage.setItem("authToken", newToken);
    setToken(newToken);
  };

  const removeToken = () => {
    localStorage.removeItem("authToken");
    setToken(null);
  };

  const fetchToken = (): Promise<string> => {
    return localStorage.get("authToken");
  };

  useEffect(() => {
    const storedToken = localStorage.getItem("authToken");
    if (storedToken) {
      setToken(storedToken);
    }
  }, []);

  const login = async (
    username: string,
    password: string
  ): Promise<SignInResponse> => {
    const response = await loginUser(username, password);
    if (response.data != null && response.data.statusCode == 200) {
      saveToken(response.data.data.token);
      setUser(username);
    }
    return response.data;
  };

  const logout = () => {
    removeToken();
    setUser(null);
  };

  const signup = async (
    username: string,
    emailId: string,
    password: string,
    role: string
  ): Promise<SignUpResponse> => {
    const response = await signupUser(username, emailId, password, role);
    let statusCode = response.data.statusCode;
    return response.data;
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, signup, fetchToken }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuthContext must be used within an AuthProvider");
  }
  return context;
};
