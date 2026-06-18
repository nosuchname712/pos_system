import { useState } from "react";
import TableSelector from "../components/TableSelector";
import MenuList from "../components/MenuList";
import Cart from "../components/Cart";

export default function POSPage() {

  const [order, setOrder] = useState(null);

  return (
    <div>
      <h1>POS System</h1>

      {!order ? (
        <TableSelector setOrder={setOrder} />
      ) : (
        <>
          <MenuList order={order} setOrder={setOrder} />
          <Cart order={order} setOrder={setOrder} />
        </>
      )}
    </div>
  );
}