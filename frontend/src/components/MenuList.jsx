import API from "../services/api";

export default function MenuList({ order, setOrder }) {

  const addItem = async (menuItemId) => {
    const res = await API.post(
      `/orders/${order.id}/items?menuItemId=${menuItemId}&quantity=1`
    );

    setOrder(res.data);
  };

  return (
    <div>
      <h2>Menu</h2>

      <button onClick={() => addItem(1)}>Burger</button>
      <button onClick={() => addItem(2)}>Fries</button>
      <button onClick={() => addItem(3)}>Coke</button>
    </div>
  );
}