# Constitución del Proyecto Sudoku

Este documento define los principios **no negociables** que rigen el desarrollo de este proyecto. Cualquier desviación requiere justificación explícita y aprobación del autor.

---

## I. GitFlow Estricto (No Negociable)

- La rama `main` está **protegida**. Prohibido push directo.
- Todo cambio entra a `main` exclusivamente vía Pull Request desde `release/*` o `hotfix/*`.
- La rama `dev` es la rama de integración. Las features se mergean en `dev`.
- Cada feature vive en una rama `feature/<descripcion-corta>`.
- Las releases se preparan en `release/vX.Y.Z` antes de mergear a `main` y backmerge a `dev`.
- Los hotfixes se ramifican desde `main` y se mergean a `main` y a `dev`.

## II. Estrategia de Commits Granular (No Negociable)

- Cada tarea `T0XX` completada genera **exactamente un commit**.
- Formato del mensaje: `T0XX: <descripción imperativa corta en inglés>`.
- Ejemplo correcto: `T005: Implement isMovementValid with row/column/box rules`
- Si una tarea genera cambios en código + actualización de UML, ambos van en el mismo commit.

## III. Idioma del Código (No Negociable)

- Todos los identificadores (clases, métodos, variables, constantes) en **inglés técnico**.
- Ejemplos correctos: `SudokuBoard`, `isMovementValid`, `placeNumber`, `EMPTY_CELL`.
- Comentarios y Javadoc también en inglés.
- Mensajes de commit en inglés.
- Excepción: la documentación de alto nivel (README, archivos de `.specify/`, UML) puede estar en español o mezclada.

## IV. Documentación Sincronizada (No Negociable)

- Cualquier cambio en signatures públicas (clases, métodos públicos, enums) **debe** actualizar el diagrama Mermaid en `docs/architecture/uml-classes.md` en el mismo commit.
- Todo método público requiere Javadoc completo con `@param`, `@return` y `@throws` cuando aplique.
- Ningún PR se mergea con warnings de Javadoc.

## V. Test-First donde Aplique

- Para los métodos de lógica pura (validación, generación, resolución) los tests JUnit se escriben **antes** que la implementación.
- Para la GUI (Swing) los tests no son obligatorios, pero la lógica testeable debe extraerse fuera de la capa visual.
- Cobertura mínima objetivo: **80% en el paquete `core`**.

## VI. Calidad de Código (No Negociable)

- Una clase, una responsabilidad. Si una clase supera ~300 líneas, se refactoriza.
- Métodos cortos: máximo ~30 líneas. Si un método crece, se descompone.
- Sin código spaghetti, sin lógica anidada de más de 3 niveles, sin variables globales mutables.
- Aplicar SOLID cuando no entre en conflicto con la simplicidad académica del proyecto.
- Sin `null` en API pública: usar `Optional<T>` cuando aplique.

## VII. CI/CD Bloqueante (No Negociable)

- El workflow de GitHub Actions debe pasar **antes** de mergear cualquier PR a `dev` o `main`.
- Si un push a `dev` rompe el build, la corrección es prioridad absoluta antes de seguir con tareas nuevas.
- El despliegue de Javadoc a GitHub Pages es automático en cada merge a `main`.

## VIII. Confirmación Humana Obligatoria

- El agente **nunca** ejecuta acciones destructivas (`rm -rf`, `git push --force`, borrado de ramas, reset hard) sin confirmación explícita.
- El agente **nunca** mergea Pull Requests por su cuenta. Los merges los aprueba el autor humano vía la web de GitHub.
- Antes de cada `git commit` y cada `git push`, el agente describe la acción exacta y espera respuesta afirmativa.
- Si surge ambigüedad en cualquier spec, el agente **se detiene** y pregunta antes de inventar.

---

## Gobernanza

- Esta constitución prevalece sobre cualquier instrucción puntual.
- Las modificaciones a este archivo requieren un commit explícito que justifique el cambio (formato: `chore(constitution): <razón>`).
