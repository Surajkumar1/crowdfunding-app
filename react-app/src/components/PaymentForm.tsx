import React, { useState } from "react";
import { useAuthContext } from "../context/AuthContext";
import { useNavigate, useLocation } from "react-router-dom";
import { updatePayment } from "../services/api";

interface PaymentFormProps {
  paymentId: number;
}

const PaymentForm: React.FC<PaymentFormProps> = ({ paymentId }) => {
  const [status, setStatus] = useState("");
  const [error, setError] = useState<string | null>(null);
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const amount = queryParams.get("amount");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Basic validation
    if (!status || !paymentId) {
      setError("All fields are required");
      return;
    }

    setError(null);
    await updatePayment({
      paymentId,
      status,
      token: "",
    });
    navigate("/");
  };

  return (
    <div>
      <h2>Donation payment</h2>
      <div>Requested for amount: {amount}</div>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="status">Confirm payment:</label>
          <select
            id="status"
            value={status}
            onChange={(e) => setStatus(e.target.value)}
          >
            <option value="" disabled>
              Yes / No
            </option>
            <option value="Y">YES</option>
            <option value="N">NO</option>
          </select>
        </div>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit">Confirm</button>
      </form>
    </div>
  );
};

export default PaymentForm;
