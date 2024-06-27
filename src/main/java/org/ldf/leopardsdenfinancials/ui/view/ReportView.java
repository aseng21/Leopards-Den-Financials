package org.ldf.leopardsdenfinancials.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import org.ldf.leopardsdenfinancials.backend.Transaction;
import org.ldf.leopardsdenfinancials.backend.service.TransactionService;
import org.ldf.leopardsdenfinancials.ui.MainLayout;
import org.vaadin.reports.PrintPreviewReport;

import java.time.LocalDate;
import java.util.List;

/**
 * View representing the financial report generation and display.
 */
@PageTitle("Report | LDF")
@Route(value = "report", layout = MainLayout.class)
@PermitAll
public class ReportView extends VerticalLayout {

    /**
     * Constructs the ReportView.
     *
     * @param transactionService The service for handling transaction-related data.
     */
    public ReportView(TransactionService transactionService) {

        // Date range for the past week
        var startDate = LocalDate.now().minusDays(7);
        var endDate = LocalDate.now();

        // Data retrieval for the report
        var totalProfitInPastWeek = transactionService.findProfitByDate(startDate, endDate);
        var totalQuantitySoldInPastWeek = transactionService.findQuantityByDate(startDate, endDate);
        var mostPopularClotheInPastWeek = transactionService.findMostPopularItemLast7Days(startDate, endDate);
        List<Transaction> transactionsInPastWeek = transactionService.findAllTransactionsByDate(startDate, endDate);

        // Create a report using Vaadin Reports addon
        var report = new PrintPreviewReport<>(Transaction.class, "clothingName", "quantity", "profit", "date");
        report.setItems(transactionsInPastWeek);
        report.getReportBuilder().setTitle("Report for " + startDate + " to " + endDate);
        report.setVisible(false);

        // Create StreamResources for PDF and CSV formats
        StreamResource pdf = report.getStreamResource("transactions.pdf", transactionService::findAll, PrintPreviewReport.Format.PDF);
        StreamResource csv = report.getStreamResource("transactions.csv", transactionService::findAll, PrintPreviewReport.Format.CSV);

        // Create anchors for PDF and CSV downloads
        var pdfAnchor = new Anchor(pdf, "PDF");
        var csvAnchor = new Anchor(csv, "CSV");
        HorizontalLayout downloadFormatAnchorLayout = new HorizontalLayout(pdfAnchor, csvAnchor);
        downloadFormatAnchorLayout.setVisible(false);

        // UI components setup
        add(
                new H1("Report"),
                new H4(String.format("Total Profit in the Past Week: $%.2f", totalProfitInPastWeek)),
                new H4("Total Clothing Sold in Past Week: " + totalQuantitySoldInPastWeek),
                new H4("Most Popular Clothing in Past Week: " + mostPopularClotheInPastWeek),
                // Button to generate the report
                new Button("Generate Report For The Past Week", event -> {
                    downloadFormatAnchorLayout.setVisible(true);
                    report.setVisible(true);
                }),
                // Anchors for PDF and CSV downloads
                downloadFormatAnchorLayout,
                // Vaadin Reports component for displaying the report
                report
        );
    }
}