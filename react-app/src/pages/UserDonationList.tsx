import React, { useState, useEffect, Component } from "react";
import {
  Project as ProjectType,
  ProjectListResponse,
  Donation,
} from "../context/ProjectContext";
import DonationForm from "../components/DonationForm";
import {
  useProjectContext,
  DonationResponse,
  Project,
} from "../context/ProjectContext";
import { useParams } from "react-router-dom";
import "../ProjectDetails.css";
import RedirectionButton from '../button/RedirectionButton'; 

const UserDonationList: React.FC = () => {
  const { fetchUserDonations } = useProjectContext();
  const [error, setError] = useState<string | null>(null);
  const [donations, setDonations] = useState<Donation[] | null>(null);
  const [hasFetched, setHasFetched] = useState<boolean>(false);
  const queryParams = new URLSearchParams(location.search);
  let page_num = queryParams.get("page_num");
  page_num = page_num === null ? "" : page_num;
  let page_size = queryParams.get("page_size");
  page_size = page_size === null ? "" : page_size;

  console.log("yo");
  const fetchData = async () => {

    if (hasFetched) return;
    setHasFetched(true);
    const getProject = async () => {
      try {
        const response = await fetchUserDonations({
          page_num,
          page_size,
        });
        console.log(response);
        if (response.statusCode == "200") {
          setDonations(response.data);
        } else {
          setError(response.message);
        }
      } catch (error) {
      }
    };
    getProject();
  };

  fetchData();

  if (donations == null) {
    return (
      <div className="project-list">
        <h1>No donations available.</h1>
        <RedirectionButton name="Home page" url="/" />
      </div>
    );
  }

  return (
    <div className="project-list">
      <h1>Our Campaigns</h1>
      <div className="project-container">
        {donations.map((donation) => (
          <div key={donation.id} className="project-card">
            <h2 className="donation-title">Donation Id: {donation.id}</h2>
            <p className="project-title">Project: {donation.campaign.title}</p>
            <p className="project-donation">Donated Amount: {donation.amount}</p>
          </div>
        ))}
      </div>
      <RedirectionButton name="Home page" url="/" />
    </div>
  );
};

export default UserDonationList;
