package dao;

import model.Transaction;
import database.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransactionDAOImplTest {

    private TransactionDAOImpl transactionDAO;
    private AutoCloseable closeable;
    private MockedStatic<DatabaseManager> databaseManagerMock;

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        transactionDAO = new TransactionDAOImpl();
        databaseManagerMock = mockStatic(DatabaseManager.class);
        databaseManagerMock.when(DatabaseManager::getConnection).thenReturn(mockConnection);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
        databaseManagerMock.close();
    }

    @Test
    public void testGetAllTransactions() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("book_id")).thenReturn(101, 102);
        when(mockResultSet.getInt("patron_id")).thenReturn(201, 202);
        when(mockResultSet.getDate("issue_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2023, 1, 1)),
                java.sql.Date.valueOf(LocalDate.of(2023, 2, 1)));
        when(mockResultSet.getDate("return_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2023, 1, 10)),
                java.sql.Date.valueOf(LocalDate.of(2023, 2, 10)));

        List<Transaction> transactions = transactionDAO.getAllTransactions();
        assertEquals(2, transactions.size());

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    public void testGetTransactionById() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("book_id")).thenReturn(101);
        when(mockResultSet.getInt("patron_id")).thenReturn(201);
        when(mockResultSet.getDate("issue_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2023, 1, 1)));
        when(mockResultSet.getDate("return_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2023, 1, 10)));

        Transaction transaction = transactionDAO.getTransactionById(1);
        assertNotNull(transaction);
        assertEquals(101, transaction.getBookId());

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    public void testAddTransaction() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Transaction transaction = new Transaction(1, 101, 201, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
        transactionDAO.addTransaction(transaction);

        verify(mockPreparedStatement, times(1)).setInt(1, transaction.getBookId());
        verify(mockPreparedStatement, times(1)).setInt(2, transaction.getPatronId());
        verify(mockPreparedStatement, times(1)).setDate(3, java.sql.Date.valueOf(transaction.getIssueDate()));
        verify(mockPreparedStatement, times(1)).setDate(4, java.sql.Date.valueOf(transaction.getReturnDate()));
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateTransaction() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Transaction transaction = new Transaction(1, 101, 201, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 10));
        transactionDAO.updateTransaction(transaction);

        verify(mockPreparedStatement, times(1)).setInt(1, transaction.getBookId());
        verify(mockPreparedStatement, times(1)).setInt(2, transaction.getPatronId());
        verify(mockPreparedStatement, times(1)).setDate(3, java.sql.Date.valueOf(transaction.getIssueDate()));
        verify(mockPreparedStatement, times(1)).setDate(4, java.sql.Date.valueOf(transaction.getReturnDate()));
        verify(mockPreparedStatement, times(1)).setInt(5, transaction.getId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteTransaction() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        transactionDAO.deleteTransaction(1);

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

}

