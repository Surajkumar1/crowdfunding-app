import React, { useState, useEffect, Component } from "react";
import { Project as ProjectType } from "../context/ProjectContext";
import DonationForm from "../components/DonationForm";
import { useProjectContext, DonationResponse } from "../context/ProjectContext";
import { useParams } from "react-router-dom";
import "../ProjectDetails.css";

const Donation: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { fetchDonation } = useProjectContext();
  const [error, setError] = useState<string | null>(null);
  const [donation, setDonation] = useState<DonationResponse | null>(null);
  const [hasFetched, setHasFetched] = useState<boolean>(false);

  console.log("yo");
  const fetchData = async () => {
    // Function to perform the login

    if (hasFetched) return;
    setHasFetched(true);
    const getProject = async () => {
      try {
        if (id != null) {
          const response = await fetchDonation(id);
          if (response.statusCode == "200") {
            setDonation(response);
          } else {
            setError(response.message);
          }
        }
      } catch (error) {
        console.error("Login failed:", error);
      }
    };
    getProject();
  };

  fetchData();
  console.log("yo2");
  if (donation == null) {
    return (
      <div>
        <h2>Donation details not available</h2>
      </div>
    );
  }
  console.log("yo3");
  let data = donation.data;
  console.log(data);
  return (
    <div className="donation-details">
      <h2>
        Thanks for donating {data.amount} towards the project -{" "}
        {data.campaign.title}
      </h2>
    </div>
  );
};

export default Donation;
