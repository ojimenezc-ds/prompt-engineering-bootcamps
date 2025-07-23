import React from 'react';

const Button = ({ label, onClick, type = 'button' }) => {
  return (
    <button
      type={type}
      className="w-full bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
      onClick={onClick}
    >
      {label}
    </button>
  );
};

export default Button;
