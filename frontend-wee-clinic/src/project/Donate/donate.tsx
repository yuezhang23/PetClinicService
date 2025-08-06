import "../../index.css"
import { Fragment, useCallback, useEffect, useMemo, useState } from "react"
import { InputSelect } from "../../components/InputSelect"
import { Instructions } from "../../components/Instructions"
import { Transactions } from "../../components/Transactions"
import { useEmployees } from "../../hooks/useEmployees"
import { useClients } from "../../hooks/useClients"
import { usePaginatedTransactions, } from "../../hooks/usePaginatedTransactions"
import { useTransactionsByEmployee } from "../../hooks/useTransactionsByEmployee"
import { useTransactionsByClient } from "../../hooks/useTransactionsByClient"
import { EMPTY_CLIENT, EMPTY_EMPLOYEE } from "../../utils/constants"
import { Employee, Client } from "../../utils/types"
import { AppContextProvider } from "src/components/AppContextProvider"


function Donate() {
    const { data: employees, ...employeeUtils } = useEmployees()
    const { data: clients, ...clientUtils } = useClients()
  
    const { data: paginatedTransactions, ...paginatedTransactionsUtils } = usePaginatedTransactions()
    const { data: transactionsByEmployee, ...transactionsByEmployeeUtils } = useTransactionsByEmployee()
    const { data: transactionsByClient, ...transactionsByClientUtils } = useTransactionsByClient()
  
    const [isLoading, setIsLoading] = useState(false)
    const [employId, setEmployId] = useState("")
    const [cId, setCId] = useState("")
    
  
    const transactions = useMemo(
      () => paginatedTransactions?.data ?? transactionsByEmployee ?? null,
      [paginatedTransactions, transactionsByEmployee]
    )
  
  
    const loadAllTransactions = useCallback(async () => {
      setIsLoading(true)
      
      transactionsByEmployeeUtils.invalidateData() 
      await employeeUtils.fetchAll()
      await clientUtils.fetchAll()
      await paginatedTransactionsUtils.fetchAll()
      
      setIsLoading(false)
    }, [employeeUtils, clientUtils, paginatedTransactionsUtils, transactionsByEmployeeUtils])
    
    
    
    const loadTransactionsByEmployee = useCallback(
      async (employeeId: string) => {
       
        paginatedTransactionsUtils.invalidateData() 
        await transactionsByEmployeeUtils.fetchById(employeeId)
      },
      [paginatedTransactionsUtils, transactionsByEmployeeUtils]
    )
  
      
    const loadTransactionsByClient = useCallback(
      async (cId: string) => {
       
        paginatedTransactionsUtils.invalidateData() 
        await transactionsByClientUtils.fetchById(cId)
      },
      [paginatedTransactionsUtils, transactionsByClientUtils]
    )
  
   
    useEffect(() => {
      if (employees === null && !employeeUtils.loading) {
        loadAllTransactions()
      }
    }, [employeeUtils.loading, employees, loadAllTransactions])
  
    return (
        <div className="p-5 w-75 border border-outline-secondary rounded">  
            <Instructions />
            <hr className="RampBreak--l" />
            <div className="RampLoading--container-split">          
              <InputSelect<Employee> 
                isLoading={isLoading}
                defaultValue={EMPTY_EMPLOYEE}
                items={employees === null ? [] : [EMPTY_EMPLOYEE, ...employees]}
                label="Filter by employee"
                loadingLabel="Loading employees"
    
                parseItem={(item) => ({
                  value: item.personal.userID,
                  label: `${item.personal.firstName} ${item.personal.lastName}`,
                })}
                onChange={async (newValue) => {
                  if (newValue === null) {
                    return
                  }
                  if (newValue === EMPTY_EMPLOYEE){
                    setEmployId("")
                    await loadAllTransactions()
                  } else {
                    setEmployId(newValue.personal.userID)
                    await loadTransactionsByEmployee(newValue.personal.userID)   
                  }
                }}
              />
            
            <InputSelect<Client>
              isLoading={isLoading}
              defaultValue={EMPTY_CLIENT}
              items={clients === null ? [] : [EMPTY_CLIENT, ...clients]}
              label="Filter by client"
              loadingLabel="Loading clients"
  
              parseItem={(item) => ({
                value: item.personal.userID,
                label: `${item.personal.firstName} ${item.personal.lastName}`,
              })}
              onChange={async (newValue) => {
                if (newValue === null) {
                  return
                }
                if (newValue === EMPTY_CLIENT){
                  setCId("")
                } else {
                  setCId(newValue.personal.userID)
                  await loadTransactionsByClient(newValue.personal.userID)   
                }
              }}
            />
          </div>
  
          <div className="RampBreak--l" />
          <div className="RampGrid">
            <Transactions transactions={transactions}/>
            {transactions !== null && (
              <button
                className= { paginatedTransactions?.nextPage === null || employId !== "" ? "d-none" : "RampButton"}
                disabled={paginatedTransactionsUtils.loading}
                onClick={async () => { await loadAllTransactions();}} >
                View More
              </button>
            )}
          </div>
        </div>
    );
  }
export default Donate;
