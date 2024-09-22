import React from "react";
import { useAuthContext } from "../context/AuthContext";
import ProjectList from "./ProjectList";
import "../homeStyles.css";
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {
  const { user, logout } = useAuthContext();

  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate("/login");
  };

  const handleSignUpClick = () => {
    navigate("/signup");
  };

  return (
    <div className="home">
      <header className="home-header">
        <h1>Welcome{user ? `, ${user}` : ""}!</h1>
        {user ? (
          <button className="logout-button" onClick={logout}>
            Logout
          </button>
        ) : (
          <div>
            <button className="login-button" onClick={handleLoginClick}>
              Login
            </button>
            <button className="signup-button" onClick={handleSignUpClick}>
              Sign Up
            </button>
          </div>
        )}
      </header>
      <main>
        <ProjectList />
      </main>
    </div>
  );
};

export default Home;
