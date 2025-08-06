import CryptoJS from "crypto-js";

// Encrypt the sensitive data
const encryptData = (data) => {
  const ciphertext = CryptoJS.AES.encrypt(data, "your-secret-key").toString();
  return encodeURIComponent(ciphertext);  // URL-encode the encrypted string
};

// Usage in navigation
const navigateWithEncryptedData = () => {
  const userInfo = JSON.stringify({ name: "John Doe", email: "john@example.com" });
  const encryptedData = encryptData(userInfo);

  // Navigate to another page with encrypted data in the URL
  window.location.href = `/next-page?data=${encryptedData}`;
};
