<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Front-Office - Liste des Réservations</title>
            <style>
                :root {
                    --primary: #6366f1;
                    --primary-hover: #4f46e5;
                    --bg: #0f172a;
                    --card-bg: rgba(30, 41, 59, 0.7);
                    --text: #f8fafc;
                    --text-dim: #94a3b8;
                }

                body {
                    font-family: 'Inter', system-ui, -apple-system, sans-serif;
                    background: var(--bg);
                    background-image:
                        radial-gradient(at 0% 0%, rgba(99, 102, 241, 0.15) 0px, transparent 50%),
                        radial-gradient(at 100% 100%, rgba(168, 85, 247, 0.15) 0px, transparent 50%);
                    color: var(--text);
                    margin: 0;
                    padding: 2rem;
                    min-height: 100vh;
                }

                .container {
                    max-width: 1000px;
                    margin: 0 auto;
                }

                h1 {
                    font-size: 2.5rem;
                    font-weight: 800;
                    margin-bottom: 2rem;
                    text-align: center;
                    background: linear-gradient(to right, #818cf8, #c084fc);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                }

                .search-box {
                    background: var(--card-bg);
                    backdrop-filter: blur(12px);
                    padding: 1.5rem;
                    border-radius: 1rem;
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    margin-bottom: 2rem;
                    display: flex;
                    gap: 1rem;
                    align-items: center;
                    justify-content: center;
                }

                input[type="date"] {
                    background: rgba(15, 23, 42, 0.6);
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    color: var(--text);
                    padding: 0.75rem 1rem;
                    border-radius: 0.5rem;
                    font-size: 1rem;
                }

                button {
                    background: var(--primary);
                    color: white;
                    border: none;
                    padding: 0.75rem 1.5rem;
                    border-radius: 0.5rem;
                    font-weight: 600;
                    cursor: pointer;
                    transition: all 0.2s;
                }

                button:hover {
                    background: var(--primary-hover);
                    transform: translateY(-1px);
                }

                .table-container {
                    background: var(--card-bg);
                    backdrop-filter: blur(12px);
                    border-radius: 1rem;
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    overflow: hidden;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th {
                    background: rgba(255, 255, 255, 0.05);
                    text-align: left;
                    padding: 1rem 1.5rem;
                    color: var(--text-dim);
                    font-weight: 600;
                    text-transform: uppercase;
                    font-size: 0.75rem;
                    letter-spacing: 0.05em;
                }

                td {
                    padding: 1rem 1.5rem;
                    border-top: 1px solid rgba(255, 255, 255, 0.05);
                }

                tr:hover td {
                    background: rgba(255, 255, 255, 0.02);
                }

                .id-client {
                    font-family: monospace;
                    background: rgba(99, 102, 241, 0.1);
                    color: #818cf8;
                    padding: 0.25rem 0.5rem;
                    border-radius: 0.25rem;
                    font-weight: 600;
                }
            </style>
        </head>

        <body>
            <div class="container">
                <h1>Liste des Réservations (Front-Office)</h1>

                <form action="reservations" method="get" class="search-box">
                    <label for="date">Rechercher par date :</label>
                    <input type="date" id="date" name="date" value="${currentDate}">
                    <button type="submit">Rechercher</button>
                    <a href="reservations"
                        style="color: var(--text-dim); text-decoration: none; font-size: 0.9rem;">Effacer</a>
                </form>

                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID Client</th>
                                <th>Passagers</th>
                                <th>Date & Heure d'Arrivée</th>
                                <th>Hôtel</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="res" items="${reservations}">
                                <tr>
                                    <td><span class="id-client">${res.idClient}</span></td>
                                    <td>${res.nombrePassager}</td>
                                    <td>${res.dateHeureArrive}</td>
                                    <td>${res.nomHotel}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty reservations}">
                                <tr>
                                    <td colspan="4" style="text-align: center; color: var(--text-dim); padding: 3rem;">
                                        Aucune réservation trouvée.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </body>

        </html>