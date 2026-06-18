import API from "../services/api";

export default function Cart({ order, setOrder }) {

  const checkout = async () => {
    const res = await API.post(`/orders/${order.id}/checkout`);
    setOrder(res.data);
  };

  const pay = async () => {
    const res = await API.post(
      `/orders/${order.id}/pay?paymentMethod=CASH`
    );
    setOrder(res.data);
  };

  return (
    <div>
      <h2>Cart</h2>

      {order?.orderItems?.map((item, idx) => (
        <div key={idx}>
          Item ID: {item.menuItemId} | Qty: {item.quantity}
        </div>
      ))}

      <h3>Total: {order.totalPrice}</h3>

      <button onClick={checkout}>Checkout</button>
      <button onClick={pay}>Pay</button>
    </div>
  );
}