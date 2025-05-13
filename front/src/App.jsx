import React from 'react';
import { Routes, Route, Link } from 'react-router-dom';
import './App.css';
import ItemList from './components/ItemList';
import ItemForm from './components/ItemForm';

export default function App() {
  return (
    <div style={{ padding: 20 }}>
      {/* 기본 틀 */}
      <header>
        <h1>React CRUD 샘플</h1>
        <nav>
          <Link to="/">목록</Link> {'|'} <Link to="/new">새 항목</Link>
        </nav>
        <hr />
      </header>

      {/* 여기가 라우팅 영역 */}
      <Routes>
        <Route path="/"        element={<ItemList />} />
        <Route path="/new"     element={<ItemForm />} />
        <Route path="/edit/:id" element={<ItemForm />} />
        {/* 매핑되지 않는 경로는 간단히 */}
        <Route path="*"        element={<div>페이지를 찾을 수 없습니다.</div>} />
      </Routes>
    </div>
  );
}