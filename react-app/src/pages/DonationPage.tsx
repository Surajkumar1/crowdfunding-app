import React from "react";
import { useParams } from "react-router-dom";
import DonationForm from "../components/DonationForm";
import RedirectionButton from '../button/RedirectionButton'; 

const DonationPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();

  return (
    <div className="donation-page">
      <h1>Donate to Project</h1>
      <DonationForm projectId={Number(id)} />
      <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default DonationPage;
