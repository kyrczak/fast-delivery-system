package pl.pg.kyrczak.jakarta.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.pg.kyrczak.jakarta.client.controller.api.ClientController;
import pl.pg.kyrczak.jakarta.client.dto.PatchClientRequest;
import pl.pg.kyrczak.jakarta.client.dto.PutClientRequest;
import pl.pg.kyrczak.jakarta.parcel.controller.api.ParcelController;
import pl.pg.kyrczak.jakarta.parcel.dto.PatchParcelRequest;
import pl.pg.kyrczak.jakarta.parcel.dto.PutParcelRequest;
import pl.pg.kyrczak.jakarta.warehouse.controller.api.WarehouseController;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Central API servlet for fetching all request from the client and preparing responses. Servlet API does not allow
 * named path parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    /**
     * Controller for managing collections characters' representations.
     */
    private ParcelController parcelController;

    /**
     * Controller for managing collections professions' representations.
     */
    private WarehouseController warehouseController;

    private ClientController clientController;

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static final class Paths {

        /**
         * All API operations. Version v1 will be used to distinguish from other implementations.
         */
        public static final String API = "/api";

    }

    /**
     * Patterns used for checking servlet path.
     */
    public static final class Patterns {

        /**
         * UUID
         */
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        /**
         * All characters.
         */
        public static final Pattern PARCELS = Pattern.compile("/parcels/?");

        /**
         * Single character.
         */
        public static final Pattern PARCEL = Pattern.compile("/parcels/(%s)".formatted(UUID.pattern()));

        /**
         * Single character's portrait.
         */
        public static final Pattern PARCEL_IMAGE = Pattern.compile("/parcels/(%s)/image".formatted(UUID.pattern()));

        /**
         * All professions.
         */
        public static final Pattern WAREHOUSES = Pattern.compile("/warehouses/?");

        /**
         * All characters of single profession.
         */
        public static final Pattern WAREHOUSE_PARCELS = Pattern.compile("/warehouses/(%s)/parcels/?".formatted(UUID.pattern()));

        /**
         * All characters of single user.
         */
        public static final Pattern CLIENT_PARCELS = Pattern.compile("/clients/(%s)/parcels/?".formatted(UUID.pattern()));

        public static final Pattern CLIENT = Pattern.compile("/clients/(%s)".formatted(UUID.pattern()));

        public static final Pattern CLIENTS = Pattern.compile("/clients/?");

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating this is expensive. The JSON-B is only one
     * of many solutions. JSON strings can be built by hand {@link StringBuilder} or with JSON-P API. Both JSON-B and
     * JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        parcelController = (ParcelController) getServletContext().getAttribute("parcelController");
        warehouseController = (WarehouseController) getServletContext().getAttribute("warehouseController");
        clientController = (ClientController) getServletContext().getAttribute("clientController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PARCELS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(parcelController.getParcels()));
                return;
            } else if (path.matches(Patterns.PARCEL.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                response.getWriter().write(jsonb.toJson(parcelController.getParcel(uuid)));
                return;
            } else if (path.matches(Patterns.WAREHOUSES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(warehouseController.getWarehouses()));
                return;
            } else if (path.matches(Patterns.WAREHOUSE_PARCELS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.WAREHOUSE_PARCELS, path);
                response.getWriter().write(jsonb.toJson(parcelController.getWarehouseParcels(uuid)));
                return;
            } else if (path.matches(Patterns.CLIENT_PARCELS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CLIENT_PARCELS, path);
                response.getWriter().write(jsonb.toJson(parcelController.getClientParcels(uuid)));
                return;
            } else if (path.matches(Patterns.PARCEL_IMAGE.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.PARCEL_IMAGE, path);
                byte[] portrait = parcelController.getParcelImage(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            } else if (path.matches(Patterns.CLIENTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(clientController.getClients()));
                return;
            } else if (path.matches(Patterns.CLIENT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CLIENT, path);
                response.getWriter().write(jsonb.toJson(clientController.getClient(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PARCEL.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                parcelController.putParcel(uuid, jsonb.fromJson(request.getReader(), PutParcelRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "parcels", uuid.toString()));
                return;
            } else if (path.matches(Patterns.PARCEL_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL_IMAGE, path);
                parcelController.putParcelImage(uuid, request.getPart("image").getInputStream());
                return;
            } else if (path.matches(Patterns.CLIENT.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                clientController.putClient(uuid, jsonb.fromJson(request.getReader(), PutClientRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "clients", uuid.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PARCEL.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                parcelController.deleteParcel(uuid);
                return;
            } else if (path.matches(Patterns.PARCEL_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL_IMAGE, path);
                parcelController.deleteParcelImage(uuid);
                return;
            } else if (path.matches(Patterns.CLIENT.pattern())) {
                UUID uuid = extractUuid(Patterns.CLIENT, path);
                clientController.deleteClient(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PATCH request.
     *
     * @param request  {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the PATCH cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the PATCH request
     */
    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.PARCEL.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                parcelController.patchParcel(uuid, jsonb.fromJson(request.getReader(), PatchParcelRequest.class));
                return;
            } else if (path.matches(Patterns.PARCEL_IMAGE.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL_IMAGE, path);
                parcelController.patchParcelImage(uuid, request.getPart("image").getInputStream());
                return;
            } else if (path.matches(Patterns.CLIENT.pattern())) {
                UUID uuid = extractUuid(Patterns.PARCEL, path);
                clientController.patchClient(uuid,jsonb.fromJson(request.getReader(), PatchClientRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     *
     * @param pattern regular expression pattern with
     * @param path    request path containing UUID
     * @return extracted UUID
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' character, that character is removed.
     *
     * @param request servlet request
     * @param paths   any (can be none) number of path elements
     * @return created url
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}

