package keel.nablarch.db;

import nablarch.core.db.connection.BasicDbConnectionFactoryForDataSource;
import nablarch.core.db.connection.ConnectionFactory;
import nablarch.core.db.connection.DbAccessExceptionFactory;
import nablarch.core.db.connection.exception.BasicDbAccessExceptionFactory;
import nablarch.core.db.dialect.Dialect;
import nablarch.core.db.dialect.H2Dialect;
import nablarch.core.db.statement.BasicSqlParameterParserFactory;
import nablarch.core.db.statement.BasicStatementFactory;
import nablarch.core.db.statement.SqlConvertor;
import nablarch.core.db.statement.SqlParameterParserFactory;
import nablarch.core.db.statement.SqlStatementExceptionFactory;
import nablarch.core.db.statement.StatementFactory;
import nablarch.core.db.statement.exception.BasicSqlStatementExceptionFactory;
import nablarch.core.db.transaction.SimpleDbTransactionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

/**
 * Nablarchのデータアクセス機能を使用するための設定。
 */
public class DbAccessConfiguration {

    /**
     * SimpleDbTransactionManagerを構築する。
     * 
     * @param connectionFactory ConnectionFactory
     * @param transactionManager PlatformTransactionManager
     * @return 構築されたインスタンス
     */
    @Bean
    public SimpleDbTransactionManager dbManager(ConnectionFactory connectionFactory,
                                                PlatformTransactionManager transactionManager) {
        return new UnmanagedSimpleDbTransactionManager(connectionFactory, transactionManager);
    }

    /**
     * BasicSqlStatementExceptionFactoryを構築する。
     * 
     * @return 構築されたインスタンス
     */
    @Bean
    public BasicSqlStatementExceptionFactory sqlStatementExceptionFactory() {
        return new BasicSqlStatementExceptionFactory();
    }

    /**
     * BasicSqlParameterParserFactoryを構築する。
     * 
     * @param sqlConvertors SqlConvertorのリスト
     * @return 構築されたインスタンス
     */
    @Bean
    public BasicSqlParameterParserFactory sqlParameterParserFactory(List<SqlConvertor> sqlConvertors) {
        BasicSqlParameterParserFactory sqlParameterParserFactory = new BasicSqlParameterParserFactory();
        sqlParameterParserFactory.setSqlConvertors(sqlConvertors);
        return sqlParameterParserFactory;
    }

    /**
     * BasicStatementFactoryを構築する。
     *
     * @param sqlStatementExceptionFactory SqlStatementExceptionFactory
     * @param sqlParameterParserFactory SqlParameterParserFactory
     * @return 構築されたインスタンス
     */
    @Bean
    @ConfigurationProperties(prefix = "nablarch.db.statement-factory")
    public BasicStatementFactory statementFactory(SqlStatementExceptionFactory sqlStatementExceptionFactory,
                                                  SqlParameterParserFactory sqlParameterParserFactory) {
        BasicStatementFactory statementFactory = new BasicStatementFactory();
        statementFactory.setSqlStatementExceptionFactory(sqlStatementExceptionFactory);
        statementFactory.setUpdatePreHookObjectHandlerList(List.of());
        statementFactory.setSqlParameterParserFactory(sqlParameterParserFactory);
        return statementFactory;
    }

    /**
     * BasicDbAccessExceptionFactoryを構築する。
     * 
     * @return 構築されたインスタンス
     */
    @Bean
    public BasicDbAccessExceptionFactory dbAccessExceptionFactory() {
        return new BasicDbAccessExceptionFactory();
    }

    /**
     * H2のDialectを構築する。
     *
     * @return 構築されたインスタンス
     */
    @Bean
    public H2Dialect dialect() {
        return new H2Dialect();
    }

    /**
     * BasicDbConnectionFactoryForDataSourceを構築する。
     *
     * @param dataSource データソース
     * @param statementFactory StatementFactory
     * @param dbAccessExceptionFactory DbAccessExceptionFactory
     * @param dialect Dialect
     * @return 構築されたインスタンス
     */
    @Bean
    @ConfigurationProperties(prefix = "nablarch.db.connection-factory")
    public BasicDbConnectionFactoryForDataSource connectionFactory(
            DataSource dataSource,
            StatementFactory statementFactory,
            DbAccessExceptionFactory dbAccessExceptionFactory,
            Dialect dialect) {
        BasicDbConnectionFactoryForDataSource connectionFactory = new BasicDbConnectionFactoryForDataSource();
        connectionFactory.setDataSource(new TransactionAwareDataSourceProxy(dataSource));
        connectionFactory.setStatementFactory(statementFactory);
        connectionFactory.setDbAccessExceptionFactory(dbAccessExceptionFactory);
        connectionFactory.setDialect(dialect);
        return connectionFactory;
    }
}
