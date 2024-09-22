import React from "react";
import { useAuthContext } from "../context/AuthContext";
import ProjectList from "./ProjectList";
import "../homeStyles.css";
import { useNavigate } from "react-router-dom";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RedirectionButton from '../button/RedirectionButton'; 
import UserProjectList from './UserProjectList';
import UserDonationList from './UserDonationList';

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
      <RedirectionButton name="Your Campaigns" url="/project/my/projects" />
      <RedirectionButton name="Your Donations" url="/donation/my/donations" />
      <RedirectionButton name="Our Campaigns" url="/projects" />
      <RedirectionButton name="Create a campaign" url="/campaign/create" />
      <main>
        <ProjectList />
      </main>
    </div>
  );
};

export default Home;
