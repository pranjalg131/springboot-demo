package com.jarvis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@ComponentScan(basePackages = "com.jarvis")
@EnableAutoConfiguration
@Configuration
@RestController
*/
@SpringBootApplication  // This line is basically an alias for 3 different annotations
@RestController
@RequestMapping("/api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.getReferenceById(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

//    @GetMapping("/greet")
//    public GreetRes greet(){
//        return new GreetRes(
//                "Hello",
//                List.of("Java", "C++", "JavaScript", "Golang"),
//                new Person("James", 23, 30_000)
//                );
//    }
//
//    record Person(String name, int age, double savings){}
//
//    record GreetRes(
//            String greet,
//            List<String> favProgrammingLangs,
//            Person person
//    ){}
}

