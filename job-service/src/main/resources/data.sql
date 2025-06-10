-- First insert users (required for the application table foreign keys)
INSERT INTO users (id, firstName, lastName, userUUID) VALUES
(1, 'John', 'Doe', '550e8400-e29b-41d4-a716-446655440001'),
(2, 'Jane', 'Smith', '550e8400-e29b-41d4-a716-446655440002'),
(3, 'Bob', 'Johnson', '550e8400-e29b-41d4-a716-446655440003');

-- Then insert companies
INSERT INTO company (id, companyUUID, name) VALUES
(1, '550e8400-e29b-41d4-a716-446655440000', 'Tech Solutions Inc.'),
(2, '6ba7b810-9dad-11d1-80b4-00c04fd430c8', 'Digital Innovations Ltd.'),
(3, '6ba7b811-9dad-11d1-80b4-00c04fd430c8', 'Software Experts LLC');

-- Then insert jobs
INSERT INTO job (id, jobUUID, company_id, title, description, location, locationType, employmentType, publishDate, expirationDate, applicationCount) VALUES
(1, '6ba7b812-9dad-11d1-80b4-00c04fd430c8', 1, 'Full-stack Developer', 'Razvoj modernih web aplikacija koristeći Spring Boot i React.', 'Sarajevo', 'ONSITE', 'FULL_TIME', '2025-05-01 10:00:00', '2025-12-31 23:59:59', 0),
(2, '6ba7b813-9dad-11d1-80b4-00c04fd430c8', 1, 'DevOps Engineer', 'Implementacija CI/CD pipeline-a i cloud infrastrukture.', 'Mostar', 'REMOTE', 'FULL_TIME', '2025-05-15 09:00:00', '2025-11-30 23:59:59', 0),
(3, '6ba7b814-9dad-11d1-80b4-00c04fd430c8', 2, 'UI/UX Designer', 'Dizajniranje korisničkih interfejsa za web i mobilne aplikacije.', 'Banja Luka', 'HYBRID', 'PART_TIME', '2025-06-01 08:00:00', '2025-09-30 23:59:59', 0),
(4, '6ba7b815-9dad-11d1-80b4-00c04fd430c8', 3, 'Data Scientist', 'Analiza podataka i razvoj mašinskog učenja.', 'Tuzla', 'REMOTE', 'CONTRACT', '2025-04-15 11:00:00', '2025-10-15 23:59:59', 0);

-- Finally insert applications (after users and jobs exist)
INSERT INTO application (id, applicationUUID, status, job_id, users_id, applicationDate) VALUES
(1, '6ba7b816-9dad-11d1-80b4-00c04fd430c8', 'PENDING', 1, 1, '2025-06-01 14:30:00'),
(2, '6ba7b817-9dad-11d1-80b4-00c04fd430c8', 'TEST_PASSED', 1, 2, '2025-06-02 10:15:00'),
(3, '6ba7b818-9dad-11d1-80b4-00c04fd430c8', 'APPROVED', 2, 3, '2025-06-03 09:00:00'),
(4, '6ba7b819-9dad-11d1-80b4-00c04fd430c8', 'REJECTED', 3, 1, '2025-06-04 16:45:00');