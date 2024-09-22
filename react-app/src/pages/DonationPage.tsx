import React from "react";
import { useParams } from "react-router-dom";
import DonationForm from "../components/DonationForm";

const DonationPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();

  return (
    <div className="donation-page">
      <h1>Donate to Project ID: {id}</h1>
      <DonationForm projectId={Number(id)} />
    </div>
  );
};

export default DonationPage;
