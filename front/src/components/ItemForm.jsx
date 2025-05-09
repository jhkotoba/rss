import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../api';

export default function ItemForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [name, setName] = useState('');

  // 수정 모드일 때 기존 데이터 불러오기
  useEffect(() => {
    if (!isEdit) return;
    (async () => {
      try {
        const res = await api.get(`/${id}`);
        setName(res.data.name);
      } catch (err) {
        console.error(err);
      }
    })();
  }, [id, isEdit]);

  // 저장 (POST or PUT)
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (isEdit) {
        await api.put(`/item/${id}`, { name });
      } else {
        await api.post('/item', { name });
      }
      navigate('/');
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>{isEdit ? '항목 수정' : '새 항목 생성'}</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            이름:{' '}
            <input
              type="text"
              value={name}
              onChange={e => setName(e.target.value)}
              required
            />
          </label>
        </div>
        <div style={{ marginTop: 10 }}>
          <button type="submit">
            {isEdit ? '수정 완료' : '생성 완료'}
          </button>{' '}
          <button type="button" onClick={() => navigate('/')}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
}