import React, { useState } from 'react';

function ProviderRegistrationForm() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    npiNumber: '',
    businessAddress: '',
    telephoneNumber: '',
    emailAddress: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form data submitted:', formData);
    // Here you would typically send the formData to your server via an API call
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Healthcare Provider Registration</h2>
      <input
        type="text"
        name="firstName"
        value={formData.firstName}
        onChange={handleChange}
        placeholder="First Name"
        required
      />
      <input
        type="text"
        name="lastName"
        value={formData.lastName}
        onChange={handleChange}
        placeholder="Last Name"
        required
      />
      <input
        type="text"
        name="npiNumber"
        value={formData.npiNumber}
        onChange={handleChange}
        placeholder="NPI Number"
        required
      />
      <input
        type="text"
        name="businessAddress"
        value={formData.businessAddress}
        onChange={handleChange}
        placeholder="Business Address"
        required
      />
      <input
        type="tel"
        name="telephoneNumber"
        value={formData.telephoneNumber}
        onChange={handleChange}
        placeholder="Telephone Number"
        required
      />
      <input
        type="email"
        name="emailAddress"
        value={formData.emailAddress}
        onChange={handleChange}
        placeholder="Email Address"
        required
      />
      <button type="submit">Register</button>
    </form>
  );
}

export default ProviderRegistrationForm;