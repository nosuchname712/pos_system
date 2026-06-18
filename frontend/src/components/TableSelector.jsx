import API from "../services/api";

export default function TableSelector({ setOrder }) {

  const selectTable = async (tableId) => {
    const res = await API.post(`/orders/table/${tableId}`);
    setOrder(res.data);
  };

  return (
    <div>
      <h2>Select Table</h2>

      {[1,2,3,4,5].map((t) => (
        <button key={t} onClick={() => selectTable(t)}>
          Table {t}
        </button>
      ))}
    </div>
  );
}