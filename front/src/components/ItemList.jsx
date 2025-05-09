import React, { useEffect, useState } from 'react';
import api from '../api';
import { Link } from 'react-router-dom';

export default function ItemList() {
  const [items, setItems] = useState([]);

  // 목록 불러오기
  const fetchItems = async () => {
    try {
      const res = await api.get('/items');
      setItems(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  // 삭제
  const handleDelete = async (id) => {
    if (!window.confirm('정말 삭제하시겠습니까?')) return;
    try {
      await api.delete(`/item/${id}`);
      fetchItems();
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => { fetchItems(); }, []);

  return (
    <div style={{ padding: 20 }}>
      <h1>Item 목록</h1>
      <Link to="/new">
        <button>새 항목 생성</button>
      </Link>
      <table border="1" cellPadding="8" style={{ marginTop: 10 }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>이름</th>
            <th>액션</th>
          </tr>
        </thead>
        <tbody>
          {items.map(item => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>
                <Link to={`/edit/${item.id}`}>
                  <button>수정</button>
                </Link>{' '}
                <button onClick={() => handleDelete(item.id)}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}