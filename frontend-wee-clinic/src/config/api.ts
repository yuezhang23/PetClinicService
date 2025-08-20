// API Configuration
export const API_CONFIG = {
    // Route all account-service calls via API Gateway by default
    API_GATEWAY: process.env.REACT_APP_API_BASE || 'http://localhost:8085',
    ACCOUNT_SERVICE: process.env.REACT_APP_ACCOUNT_API || `${process.env.REACT_APP_API_BASE || 'http://localhost:8085'}/accounts`,
    CLINIC_SERVICE: process.env.REACT_APP_CLINIC_API || `${process.env.REACT_APP_API_BASE || 'http://localhost:8085'}/clinic`
};

// Default fallback URLs for development
export const DEFAULT_URLS = {
    API_GATEWAY: 'http://localhost:8085',
    ACCOUNT_SERVICE: 'http://localhost:8085/accounts',
    CLINIC_SERVICE: 'http://localhost:8085/clinic'
};

// Get the appropriate URL based on environment
export const getAccountServiceUrl = () => API_CONFIG.ACCOUNT_SERVICE || DEFAULT_URLS.ACCOUNT_SERVICE;
export const getClinicServiceUrl = () => API_CONFIG.CLINIC_SERVICE || DEFAULT_URLS.CLINIC_SERVICE;
export const getApiGatewayUrl = () => API_CONFIG.API_GATEWAY || DEFAULT_URLS.API_GATEWAY;
