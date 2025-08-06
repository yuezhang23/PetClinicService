import { useCallback, useState } from "react"
import { Client } from "../utils/types"
import { useCustomFetch } from "./useCustomFetch"
import { ClientResult } from "./types"

export function useClients(): ClientResult {
  const { fetchWithCache, loading } = useCustomFetch()
  const [clients, setClients] = useState<Client[] | null>(null)

  const fetchAll = useCallback(async () => {
    const clientsData = await fetchWithCache<Client[]>("clients")
    setClients(clientsData)
  }, [fetchWithCache])

  const invalidateData = useCallback(() => {
    setClients(null)
  }, [])

  return { data: clients, loading, fetchAll, invalidateData }
}
