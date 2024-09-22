import React from "react";
import SignUpForm from "../components/SignUpForm";
import RedirectionButton from '../button/RedirectionButton'; 

const SignUpPage: React.FC = () => {
  return (
    <div className="signup-page">
      <SignUpForm />
      <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default SignUpPage;
