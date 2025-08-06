package com.example.WeePetClinic.Components.Service.Account;

import com.example.WeePetClinic.Components.Model.UserOri;
import com.example.WeePetClinic.Components.Model.forPostSql.User.UserEmpAccountantImp;
import com.example.WeePetClinic.Components.Model.forSql.User.UserEmployeeImpl;
import com.example.WeePetClinic.Components.Repository.repoSqlEmpAccountant;
import com.example.WeePetClinic.Components.Repository.repoPostSqlUserAccountant;
import com.example.WeePetClinic.utilities.TypeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@Component
@Service
public class repoAccessForAccountant {
  // Available repositories
  private final repoSqlEmpAccountant repoAccountant;
  private final repoPostSqlUserAccountant repoPosAccountant;

  @Autowired
  public repoAccessForAccountant(repoSqlEmpAccountant repoAccountant, 
                                 repoPostSqlUserAccountant repoPosAccountant) {
    this.repoAccountant = repoAccountant;
    this.repoPosAccountant = repoPosAccountant;
  }

  public UserEmpAccountantImp findAccountantById(String userID) {
    return repoPosAccountant.findById(userID).orElseThrow(()-> new NoSuchElementException("accountant not found"));
  }

  public void addAccountant(UserEmpAccountantImp emp) {
    repoPosAccountant.save(emp);
  }

  public void deleteAccountantById(String id) {
    repoPosAccountant.deleteById(id);
  }

  // Simplified methods that only use available repositories
  public Optional<UserEmployeeImpl> findEmpAccountantById(String id) {
    return repoAccountant.findById(id);
  }

  // Placeholder methods for functionality that would need additional repositories
  public UserOri findUserByIdAndType(TypeUser type, String userID) {
    // This would need additional repository implementations
    throw new UnsupportedOperationException("Method not implemented - requires additional repositories");
  }

  public Map<TypeUser, String> validateMultipleRole(String userID, String password) {
    // This would need additional repository implementations
    throw new UnsupportedOperationException("Method not implemented - requires additional repositories");
  }
}
