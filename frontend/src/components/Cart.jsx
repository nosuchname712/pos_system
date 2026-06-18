import API from "../services/api";

export default function Cart({ order, setOrder }) {

  const checkout = async () => {
    const res = await API.post(`/orders/${order.id}/checkout`);
    setOrder(res.data);
  };

  const pay = async () => {
    const res = await API.post(`/orders/${order.id}/pay?paymentMethod=CASH`);
    setOrder(res.data);
  };

  return (
    <div>
      <h2>Cart</h2>

      <p>Order ID: {order.id}</p>
      <p>Status: {order.status}</p>

      <button onClick={checkout}>Checkout</button>
      <button onClick={pay}>Pay</button>
    </div>
  );
}