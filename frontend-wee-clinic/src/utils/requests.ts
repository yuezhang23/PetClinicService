import {
  PaginatedRequestParams,
  PaginatedResponse,
  RequestByEmployeeParams,
  SetTransactionApprovalParams,
  Transaction,
  Employee,
  Client,
  Doner,
} from "./types"
import mockData from "../mock-data.json"

export const TRANSACTIONS_PER_PAGE = 5

export const dataOri: { employees: Employee[]; transactions: Transaction[]; clients : Client[]; doners: Doner[]} = {
  employees: mockData.employees,
  transactions: mockData.transactions,
  clients: mockData.clients,
  doners: mockData.doners
}

export const getEmployees = (): Employee[] => dataOri.employees
export const getClients = (): Client[] => dataOri.clients

export const getTransactionsPaginated = ({
  data = dataOri.transactions,
  page,
}: PaginatedRequestParams<Transaction[]>): PaginatedResponse<Transaction[]> => {
  if (page === null) {
    throw new Error("Page cannot be null")
  }

  // const start = page * TRANSACTIONS_PER_PAGE
  // const end = start + TRANSACTIONS_PER_PAGE
  const start = 0
  const end = page * TRANSACTIONS_PER_PAGE + TRANSACTIONS_PER_PAGE

  if (start > data.length) {
    throw new Error(`Invalid page ${page}`)
  }

  const nextPage = end < data.length ? page + 1 : null

  console.log("pageTran: " + data.at(0)?.approved)

  return {
    nextPage,
    data: data.slice(start, end),
  }
}

export const getTransactionsByEmployee = ({ employeeId }: RequestByEmployeeParams) => {
  if (!employeeId) {
    throw new Error("Employee id cannot be empty")
  }
  
  return dataOri.transactions.filter((transaction) => transaction.employee.personal.userID === employeeId)
}


export const setTransactionApproval = ({ transactionId, value }: SetTransactionApprovalParams): void => {
  const transaction = dataOri.transactions.find(
    (currentTransaction) => currentTransaction.id === transactionId
  )
  
  if (!transaction) {
    throw new Error("Invalid transaction to approve")
  }
  
  transaction.approved = value
  dataOri.transactions = dataOri.transactions.map((i) => i.id === transactionId? transaction : i)
  // console.log("here: " + dataOri.transactions.at(0)?.approved)
}
