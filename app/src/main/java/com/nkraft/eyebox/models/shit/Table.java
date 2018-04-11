package com.nkraft.eyebox.models.shit;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<Terms> termsList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<CustomerAll> customerAllList = new ArrayList<>();
    private List<Employee> employeeList = new ArrayList<>();
    private List<Source> sourceList = new ArrayList<>();
    private List<Bank> bankList = new ArrayList<>();

    public void addTerms(Terms terms) {
        termsList.add(terms);
    }
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }
    public void addCustomerAll(CustomerAll customerAll) {
        customerAllList.add(customerAll);
    }
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }
    public void addSource(Source source) {
        sourceList.add(source);
    }
    public void addBank(Bank bank) {
        bankList.add(bank);
    }

    public List<Terms> getTermsList() {
        return termsList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<CustomerAll> getCustomerAllList() {
        return customerAllList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public List<Source> getSourceList() {
        return sourceList;
    }

    public List<Bank> getBankList() {
        return bankList;
    }
}
