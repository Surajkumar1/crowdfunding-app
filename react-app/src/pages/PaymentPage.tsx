import React from "react";
import PaymentForm from "../components/PaymentForm";
import { useParams } from "react-router-dom";

const PaymentPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  return (
    <div className="payment-page">
      <PaymentForm paymentId={Number(id)} />
    </div>
  );
};

export default PaymentPage;
