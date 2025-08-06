import { useState } from "react"
import { InputCheckbox } from "../InputCheckbox"
import { TransactionPaneComponent } from "./types"


export const TransactionPane: TransactionPaneComponent = ({
  transaction,
  loading,
  setTransactionApproval: consumerSetTransactionApproval,
}) => {
  const [approved, setApproved] = useState(transaction.approved)

  return (
    <div className="RampPane">
      <div className="RampPane--content">
        <p className="RampText">{transaction.doner.name} </p>
        <b>{moneyFormatter.format(transaction.amount)}</b>
        <p className="RampText--hushed RampText--s">
          Settler: {transaction.employee.firstName} {transaction.employee.lastName} - {transaction.date}
        </p>
      </div>
      <InputCheckbox
        id={transaction.id}
        checked={approved}
        disabled={loading}
        onChange={() => {
          consumerSetTransactionApproval({
            transactionId: transaction.id,
            newValue : !approved,
          })
          setApproved(!approved)
        }}
      />
    </div>
  )
}

const moneyFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
})
