package bll;

import bll.validators.Validator;
import bll.validators.ageValidator;
import bll.validators.emailValidator;
import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new emailValidator());
        validators.add(new ageValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Retrieves all clients from the database.
     * @return A list containing all clients.
     */
    public List<Client> getAllClients() { return clientDAO.findAll(); }

    /**
     * Retrieves a client by its ID from the database.
     * @param id The ID of the client to retrieve.
     * @return The client with the specified ID.
     */
    public Client getClientById(int id) { return clientDAO.findById(id); }

    /**
     * Adds a new client to the database.
     * @param client The client to be added.
     */
    public void addClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.insert(client);
    }

    /**
     * Updates an existing client in the database.
     * @param client The client to be updated.
     */
    public void updateClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientDAO.update(client);
    }

    /**
     * Deletes a client from the database by its ID.
     * @param id The ID of the client to be deleted.
     */
    public void deleteClient(int id) { clientDAO.delete(id); }
}