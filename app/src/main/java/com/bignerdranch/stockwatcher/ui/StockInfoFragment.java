package com.bignerdranch.stockwatcher.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.stockwatcher.R;
import com.bignerdranch.stockwatcher.StockWatcherApplication;
import com.bignerdranch.stockwatcher.databinding.FragmentStockInfoBinding;
import com.bignerdranch.stockwatcher.model.service.StockInfoForSymbol;
import com.bignerdranch.stockwatcher.model.service.repository.StockDataRepository;
import com.bignerdranch.stockwatcher.util.RxUtil;

import java.util.NoSuchElementException;

import javax.inject.Inject;

import io.reactivex.Observable;

public class StockInfoFragment extends RxFragment {

    @Inject
    StockDataRepository stockDataRepository;

    private FragmentStockInfoBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StockWatcherApplication.getAppComponent(getContext()).inject(this);
        super.onCreate(savedInstanceState); // TODO: Is there a reason this is called second?
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_info, container, false);

        binding.fetchDataButton.setOnClickListener(v -> {
            binding.errorMessage.setVisibility(View.GONE);
            loadRxData();
        });

        binding.tickerSymbol.setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                loadRxData();
                return true;
            }
            return false;
        });

        binding.clearCacheButton.setOnClickListener(v -> {
            stockDataRepository.clearCache();
            Toast.makeText(getContext(), "observable cache cleared!", Toast.LENGTH_LONG).show();
        });

        return binding.getRoot();
    }

    @Override
    public void loadRxData() {
        Observable.just(binding.tickerSymbol.getText().toString())
                .filter(symbolText -> symbolText.length() > 0)
                .flatMap(s -> stockDataRepository.getStockInfoForSymbol(s))
                .compose(RxUtil.applyUIDefaults(StockInfoFragment.this))
                .subscribe(this::displayStockResults, this::displayErrors);
    }

    private void displayErrors(Throwable throwable) {
        String message = throwable.getMessage();
        if (throwable instanceof NoSuchElementException) {
            message = "Stock symbol not found";
        }

        binding.errorMessage.setVisibility(View.VISIBLE);
        binding.errorMessage.setText(message);
    }

    private void displayStockResults(StockInfoForSymbol stockInfoForSymbol) {
        binding.stockValue.setText(stockInfoForSymbol.toString());
    }
}
