package com.binance.api.client.impl.sync;

import com.binance.api.client.api.sync.BinanceApiSwapRestClient;
import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.SwapRemoveType;
import com.binance.api.client.domain.account.*;
import com.binance.api.client.api.BinanceApiService;
import com.binance.api.client.impl.BinanceApiServiceGenerator;

import java.util.List;

import static com.binance.api.client.impl.BinanceApiServiceGenerator.executeSync;

/**
 * Implementation of Binance's SWAP REST API using Retrofit method calls.
 */
public class BinanceApiSwapRestClientImpl implements BinanceApiSwapRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiSwapRestClientImpl(String apiKey, String secret, String apiUrl) {
        binanceApiService = BinanceApiServiceGenerator.createService(BinanceApiService.class, apiKey, secret, apiUrl);
    }

    @Override
    public List<Pool> listAllSwapPools() {
        return executeSync(binanceApiService.listAllSwapPools());
    }

    @Override
    public Liquidity getPoolLiquidityInfo(String poolId) {
        long timestamp = System.currentTimeMillis();
        List<Liquidity> liquidities = executeSync(binanceApiService.getPoolLiquidityInfo(poolId,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
        if (liquidities != null && !liquidities.isEmpty()) {
            return liquidities.get(0);
        }
        return null;
    }

    @Override
    public LiquidityOperationRecord addLiquidity(String poolId, String asset, String quantity) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.addLiquidity(poolId,
                asset,
                quantity,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public LiquidityOperationRecord removeLiquidity(String poolId, SwapRemoveType type, List<String> asset, String shareAmount) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.removeLiquidity(poolId,
                type,
                asset,
                shareAmount,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public List<LiquidityOperationRecord> getPoolLiquidityOperationRecords(String poolId, Integer limit) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.getPoolLiquidityOperationRecords(
                poolId,
                limit,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));

    }

    @Override
    public List<LiquidityOperationRecord> getPoolLiquidityOperationRecords(Long operationId, Long poolId, String operation, Long startTime, Long endTime, Long limit) {
        return executeSync(binanceApiService.getLiquidityOperationRecord(
                operationId,
                poolId,
                operation,
                startTime,
                endTime,
                limit,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                System.currentTimeMillis()));
    }

    @Override
    public List<LiquidityOperationRecord> getLiquidityOperationRecord(Long operationId) {
        return getPoolLiquidityOperationRecords(operationId,null,null,null,null,null);
    }

    @Override
    public SwapQuote requestQuote(String quoteAsset,
                                  String baseAsset,
                                  String quoteQty) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.requestQuote(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public SwapRecord swap(String quoteAsset, String baseAsset, String quoteQty) {
        long timestamp = System.currentTimeMillis();
        return executeSync(binanceApiService.swap(quoteAsset, baseAsset, quoteQty,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                timestamp));
    }

    @Override
    public List<SwapHistory> getSwapHistory(Long swapId, Long startTime, Long endTime, Integer status, String quoteAsset, String baseAsset, Long limit) {
        return   executeSync(binanceApiService.getSwapHistory(
                swapId,
                startTime,
                endTime,
                status,quoteAsset,
                baseAsset,
                limit,
                BinanceApiConstants.DEFAULT_RECEIVING_WINDOW,
                System.currentTimeMillis()));
    }

    @Override
    public List<SwapHistory> getSwapHistory() {
        return getSwapHistory(null,null,null,null,null,null,null);
    }

}