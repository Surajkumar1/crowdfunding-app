import React from "react";
import LoginForm from "../components/LoginForm";
import RedirectionButton from '../button/RedirectionButton'; 

const LoginPage: React.FC = () => {
  return (
    <div className="login-page">
      <LoginForm />
      <RedirectionButton name="Register" url="/singup" />
      <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default LoginPage;
