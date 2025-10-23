Portfolio Manager with Stock Prediction
Overview

The Portfolio Manager is a Java-based application that allows users to manage their stock portfolio efficiently. It supports buying, selling, and updating stocks, tracking portfolio value, and even predicting future stock prices using a simple linear regression model.

Features

Portfolio Management

Add or remove money from the portfolio.

Buy stocks by specifying company, symbol, quantity, and price.

Sell stocks individually, by company, or sell all at once.

Track the current portfolio value.

Stock Tracking

Display all stocks in the portfolio.

Display stocks of a particular company.

Highlight stocks with the highest or lowest price per share.

Identify stocks currently in profit or in loss.

Stock Price Prediction

Use historical stock prices with timestamps to predict future prices.

Linear regression model calculates trend based on previous stock price updates.

Security

All financial operations are password protected.

Only authorized users can buy, sell, or remove funds.

###Technologies Used###

Java: Core programming language.

Vector and Iterator: For dynamic stock and price storage.

Linear Regression: Basic predictive analytics on stock prices.

###Classes###

Stock

Represents a single stock with attributes like symbol, company name, quantity, price per share, stock ID, and historical prices.

Methods to update price, track history, and display stock info.

Portfolio

Manages multiple stocks and portfolio balance.

Methods to add/remove funds, buy/sell stocks, and display stocks.

Password-protected actions for security.

Predict

Performs linear regression on stock price history to predict future stock prices.

Methods to read stock history and compute predictions.

Project (Main Class)

Menu-driven interface for interacting with the portfolio.

Allows users to execute all operations including prediction.
