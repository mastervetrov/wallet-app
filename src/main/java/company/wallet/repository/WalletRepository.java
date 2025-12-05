package company.wallet.repository;

import company.wallet.model.Wallet;
import company.wallet.response.WalletBalanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Transactional
    @Modifying
    @Query("""
            UPDATE Wallet w
            SET w.balance = w.balance + :amount,
                w.availableBalance = w.availableBalance + :amount,
                w.version = w.version + 1,
                w.updatedAt = CURRENT_TIMESTAMP
            WHERE w.id = :id
            AND w.status = 'ACTIVE'
            AND w.balance + :amount <= :maxBalance
            """)
    int depositAtomic (@Param("id") UUID id,
                       @Param("amount") BigDecimal amount,
                       @Param("maxBalance") BigDecimal maxBalance);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Wallet w
            SET w.balance = w.balance - :amount,
                w.availableBalance = w.availableBalance - :amount,
                w.version = w.version + 1,
                w.updatedAt = CURRENT_TIMESTAMP
            WHERE w.id = :id
            AND w.status = 'ACTIVE'
            AND w.balance - :amount >= :minBalanceAfterWithdrawal
            AND w.availableBalance >= :amount
            """)
    int withdrawAtomic(@Param("id") UUID id,
                               @Param("amount") BigDecimal amount,
                               @Param("minBalanceAfterWithdrawal") BigDecimal minBalanceAfterWithdrawal);


    @Query("SELECT w.balance FROM Wallet w WHERE w.id = :walletId")
    Optional<BigDecimal> findBalanceById(@Param("walletId") UUID walletId);

}
