import React, { useState } from "react";
import { useProjectContext } from "../context/ProjectContext";
import { useNavigate } from "react-router-dom";

interface DonationFormProps {
  projectId: number;
}

const DonationForm: React.FC<DonationFormProps> = ({ projectId }) => {
  const { donate } = useProjectContext();
  const [amount, setAmount] = useState(0);
  const navigate = useNavigate();

  const handleDonate = async () => {
    if (amount > 0) {
      const response = await donate(projectId, amount);
      //setAmount(0);
      navigate("/payment/" + response.data.paymentId + "?amount=" + amount);
    }
  };

  return (
    <div>
      <input
        type="number"
        value={amount}
        onChange={(e) => setAmount(Number(e.target.value))}
        placeholder="Donation Amount"
        min="1"
      />
      <button onClick={handleDonate}>Donate</button>
    </div>
  );
};

export default DonationForm;
