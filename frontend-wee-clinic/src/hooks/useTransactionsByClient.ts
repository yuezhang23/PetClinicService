import { useCallback, useState } from "react"
import { RequestByClientParams, Transaction } from "../utils/types"
import { TransactionsByClientResult } from "./types"
import { useCustomFetch } from "./useCustomFetch"

export function useTransactionsByClient(): TransactionsByClientResult {
  const { fetchWithCache, loading } = useCustomFetch()
  const [transactionsByClient, setTransactionsByClient] = useState<Transaction[] | null>(null)

  const fetchById = useCallback(
    async (clientId: string) => {
      const data = await fetchWithCache<Transaction[], RequestByClientParams>(
        "transactionsByClient",
        {
          clientId,
        }
      )

      setTransactionsByClient(data)
    },
    [fetchWithCache]
  )

  const invalidateData = useCallback(() => {
    setTransactionsByClient(null)
  }, [])

  return { data: transactionsByClient, loading, fetchById, invalidateData }
}
