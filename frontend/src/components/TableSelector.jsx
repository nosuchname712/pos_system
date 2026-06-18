import API from "../services/api";

export default function TableSelector({ setOrder }) {

  const selectTable = async (tableId) => {
    try {
      const res = await API.post(`/orders/table/${tableId}`);
      setOrder(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <div>
      <h2>Select Table</h2>

      {[1,2,3,4,5].map(id => (
        <button key={id} onClick={() => selectTable(id)}>
          Table {id}
        </button>
      ))}
    </div>
  );
}