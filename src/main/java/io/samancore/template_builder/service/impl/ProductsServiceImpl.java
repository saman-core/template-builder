package io.samancore.template_builder.service.impl;

import io.samancore.template_builder.client.GitClient;
import io.samancore.template_builder.model.*;
import io.samancore.template_builder.service.ProductsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

import static io.samancore.template_builder.GitConstants.*;

@ApplicationScoped
public class ProductsServiceImpl implements ProductsService {
    private static final String PRODUCTS_SLASH = PRODUCTS.concat(SLASH);

    @Inject
    GitClient client;

    @Override
    public List<Node> listProducts(AccessInfoRecord accessInfoRecord) {
        return client.listDirectories(PRODUCTS, accessInfoRecord);
    }

    @Override
    public Node getProduct(String product, AccessInfoRecord accessInfoRecord) {
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(INFO_FILE);
        return client.getFile(file, accessInfoRecord);
    }

    @Override
    public List<Node> listTemplates(String product, AccessInfoRecord accessInfoRecord) {
        var path = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES);
        return client.listDirectories(path, accessInfoRecord);
    }

    @Override
    public Node getTemplateJson(String product, String template, AccessInfoRecord accessInfoRecord) {
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(FORM_FILE);
        return client.getFile(file, accessInfoRecord);
    }

    @Override
    public Node persistTemplate(String product, String template, CommitRequest commitRequest, Author author, AccessInfoRecord accessInfoRecord) {
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(FORM_FILE);

        var message = commitRequest.getMessage();
        var content = commitRequest.getData().getContent();
        var sha = commitRequest.getData().getId();
        return client.persistFile(file, message, content, sha, author, accessInfoRecord);
    }

    @Override
    public ConditionsProperty getConditionsProperty(String product, String template, String property, AccessInfoRecord accessInfoRecord) {
        var path = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(CONDITIONS);
        var mapConditions = client.getMapConditionsTemplate(path, accessInfoRecord);
        return mapConditions.get(property);
    }

    @Override
    public List<ConditionsProperty> getConditionsTemplate(String product, String template, AccessInfoRecord accessInfoRecord) {
        var path = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(CONDITIONS);
        var mapConditions = client.getMapConditionsTemplate(path, accessInfoRecord);
        return List.copyOf(mapConditions.values());
    }

    @Override
    public Node getConditionProperty(String product, String template, String property, ConditionType type, AccessInfoRecord accessInfoRecord) {
        var conditionName = property.concat(type.getSuffix()).concat(DMN_EXTENSION);
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(CONDITIONS).concat(SLASH).concat(conditionName);
        return client.getFile(file, accessInfoRecord);
    }

    @Override
    public Node persistConditionProperty(String product, String template, String property, ConditionType type, CommitRequest commitRequest, Author author, AccessInfoRecord accessInfoRecord) {
        var conditionName = property.concat(type.getSuffix()).concat(DMN_EXTENSION);
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(CONDITIONS).concat(SLASH).concat(conditionName);

        var message = commitRequest.getMessage();
        var content = commitRequest.getData().getContent();
        var sha = commitRequest.getData().getId();
        return client.persistFile(file, message, content, sha, author, accessInfoRecord);
    }

    @Override
    public Node deleteConditionProperty(String product, String template, String property, ConditionType type, CommitRequest commitRequest, Author author, AccessInfoRecord accessInfoRecord) {
        var conditionName = property.concat(type.getSuffix()).concat(DMN_EXTENSION);
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(TEMPLATES).concat(SLASH).concat(template).concat(SLASH).concat(CONDITIONS).concat(SLASH).concat(conditionName);

        var message = commitRequest.getMessage();
        var sha = commitRequest.getData().getId();
        return client.deleteFile(file, message, sha, author, accessInfoRecord);
    }

    @Override
    public Node getWorkflowJson(String product, AccessInfoRecord accessInfoRecord) {
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(WORKFLOW_FILE);
        return client.getFile(file, accessInfoRecord);
    }

    @Override
    public Node persistWorkflow(String product, CommitRequest commitRequest, Author author, AccessInfoRecord accessInfoRecord) {
        var file = PRODUCTS_SLASH.concat(product).concat(SLASH).concat(WORKFLOW_FILE);

        var message = commitRequest.getMessage();
        var content = commitRequest.getData().getContent();
        var sha = commitRequest.getData().getId();
        return client.persistFile(file, message, content, sha, author, accessInfoRecord);
    }
}
