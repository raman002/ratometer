insert into role_master(role_master_id, role_name)
values (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'SUPER_ADMIN');

select setval('role_master_role_master_id_seq', max(role_master_id)) from role_master;

insert into users (users_id, username, email, full_name, password)
values
-- password : steve
(1, 'steve.adams', 'steve.adams@ratometer.com', 'Steve Adams',
 '$2y$10$f7/KYCl04dGslZPgO0Lpj.G4OXgql5DS76aQmrepkfUtYPBmmvQci'),
-- password : mark
(2, 'mark.paul', 'mark.paul@ratometer.com', 'Mark Paul',
 '$2y$10$26rO7JRv41YNYmSuo425VuOtKYOvHFv7INT/8IFY37ZJt3nyenOAS'),
-- password : vikas
(3, 'vikas.singh', 'vikas.singh@ratometer.com', 'Vikas Singh',
 '$2y$10$Uahg.IPKpu/fiOR4jBh7Cu8i18SoLuePuBubG0MClnslAcSy7IWc2'),
-- password : rohit
(4, 'rohit.thakur', 'rohit.thakur@ratometer.com', 'Rohit Thakur',
 '$2y$10$TcZ352MixlHlLxklQSC.QeHNmHYaFGAGrNxwljz9v3DTsE1iptE/C');

select setval('users_users_id_seq', max(users_id)) from users;

-- Assigning Roles to the users
insert into roles(users_id, role_master_id)
values (1, 3), -- Assigning SUPER ADMIN role to Steve Adams.
       (2, 1), -- Assigning USER role to Mark Paul.
       (3, 1), -- Assigning USER role to Vikas Singh.
       (4, 1);
-- Assigning USER role to Rohit Thakur.

select setval('roles_roles_id_seq', max(roles_id)) from roles;

-- Creating teams
insert into teams_master(teams_master_id, name)
values (1, 'TEAM A'),
       (2, 'TEAM B'),
       (3, 'TEAM C');

select setval('teams_master_teams_master_id_seq', max(teams_master_id)) from teams_master;


-- Assigning Teams to the users
insert into teams(teams_id, users_id, teams_master_id)
values (1, 2, 1), -- Assigning mark to TEAM A
       (2, 3, 2), -- Assigning vikas to TEAM B
       (3, 4, 3);
-- Assigning rohit to TEAM C
select setval('teams_teams_id_seq', max(teams_id)) from teams;

-- Categories
insert into categories_master(categories_master_id, name, type)
values (1, 'Strategy and businesspartnership', 'CATEGORY'),
       (2, 'Cont Improvement Culture', 'CATEGORY'),
       (3, 'Requirement and Prioritization', 'CATEGORY'),
       (4, 'Effective Agile Process', 'CATEGORY'),
       (5, 'Book of Work Management', 'CATEGORY'),
       (6, 'Agile Organization', 'CATEGORY'),
       (7, 'Source Control', 'CATEGORY'),
       (8, 'Standardization and BuildChain', 'CATEGORY'),
       (9, 'Artefact Management', 'CATEGORY'),
       (10, 'Automation', 'CATEGORY'),
       (11, 'Quality in Design and Coding', 'CATEGORY'),
       (12, 'Continuous Integration', 'CATEGORY');
-- Sub categories
insert into categories_master(categories_master_id, parent_category_id, name, type)
values (13, 1, 'Business and Technical Strategy', 'SUB_CATEGORY'),
       (14, 1, 'Business-IT Partnership', 'SUB_CATEGORY'),
       (15, 2, 'Continuous Improvement', 'SUB_CATEGORY'),
       (16, 3, 'Requirements Quality / Defination of Ready', 'SUB_CATEGORY'),
       (17, 3, 'Prioritization & Regular Stakeholder Feedback', 'SUB_CATEGORY'),
       (18, 4, 'Planning And Estimation', 'SUB_CATEGORY'),
       (19, 4, 'Potentially Shipable Product', 'SUB_CATEGORY'),
       (20, 4, 'Definition of Done', 'SUB_CATEGORY'),
       (21, 5, 'Backlog Management & Progress Transparency', 'SUB_CATEGORY'),
       (22, 6, 'Team Design and Roles', 'SUB_CATEGORY'),
       (23, 7, 'Source Control/ Collective Code Ownership', 'SUB_CATEGORY'),
       (24, 8, 'Build Infrastructure', 'SUB_CATEGORY'),
       (25, 9, 'Artefact Management', 'SUB_CATEGORY'),
       (26, 10, 'Test Automation', 'SUB_CATEGORY'),
       (27, 11, 'Technical Debt management and Code Quality', 'SUB_CATEGORY'),
       (28, 12, 'Continuous Integration', 'SUB_CATEGORY');

-- Options

insert into categories_master(categories_master_id, parent_category_id, name, type)
values (29, 13, 'Limited Understanding of Business Goal and Technicl Strategy', 'OPTION'),
       (30, 13, 'Business Strategy and Technical Strategy Partly Exist', 'OPTION'),
       (31, 13, 'Business and Technical Strategist and working collaberatively', 'OPTION'),
       (32, 13, 'The Entire team has good Understanding of Business Goals', 'OPTION'),
       (33, 13, 'The Team partner with the business to suggest improvmentsbased on technical/Business trends',
        'OPTION'),

       (34, 14, 'Business reps not available on regular basis', 'OPTION'),
       (35, 14, 'Team have to request access to business representatives, BA''s or Proxies', 'OPTION'),
       (36, 14, 'Access to business is ensured within a day', 'OPTION'),
       (37, 14, 'Business and IT co-located', 'OPTION'),
       (38, 14, 'Proven ROI/ Delivered Business value exist', 'OPTION'),

       (39, 15, 'Team is not aware about retrospectives', 'OPTION'),
       (40, 15, 'Retrospectives ar not held or held infrequently and /or not follow-up', 'OPTION'),
       (41, 15, 'Retrospectives are held at the end of each sprint', 'OPTION'),
       (42, 15, 'Continuous Improvement cuture is in place', 'OPTION'),
       (43, 15, 'Teams strives to identify new oppurtunities for improvement or business growth', 'OPTION'),

       (44, 16, 'Team often starts development  with unclear requirements', 'OPTION'),
       (45, 16, 'Clear quality expectations for requirements( eg definition of ready) is not agreed', 'OPTION'),
       (46, 16,
        'Quality expectations for a requirement are defined(eg definition of ready)  and lived with the business',
        'OPTION'),
       (47, 16, 'Operating + Business well integrated with mature product owner role', 'OPTION'),
       (48, 16, 'Team and Business establised and optimizes a shared method such as SBE(Specification by example)',
        'OPTION'),

       (49, 17, 'No Review feedback cycle with business Unclear prioritization', 'OPTION'),
       (50, 17, 'Irregular feedback meetings Iteration scope unstable', 'OPTION'),
       (51, 17, 'Review meeting held regularly', 'OPTION'),
       (52, 17, 'Iteration scope>80% stable', 'OPTION'),
       (53, 17, 'Regular review meetings always held with business', 'OPTION'),

       (54, 18, 'Planning is done by individual for large piece of work', 'OPTION'),
       (55, 18, 'Team offen overcommit work. Team velocity not well understood', 'OPTION'),
       (56, 18, 'Team plan and estimates reliably and collaboratively', 'OPTION'),
       (57, 18, 'Estimation is rapid and consistently institutionalised with the team', 'OPTION'),
       (58, 18, 'All work items are broken down by the team into similar sizes', 'OPTION'),

       (59, 19, 'There is one big delivery at the end of the development and test window', 'OPTION'),
       (60, 19, 'Team rarely delivers Potentially Shipable Product per iteration', 'OPTION'),
       (61, 19, 'The Team produces a Potentially Shipable Product after around 50 % of the iterations', 'OPTION'),
       (62, 19, 'Team can deliver Potentially Shipable Product after every iteration', 'OPTION'),
       (63, 19, 'The team delivers a Potentially Shipable Product multiple times per iteration', 'OPTION'),

       (64, 20, 'Team does not have a shared understanding of definition of done (DOD)', 'OPTION'),
       (65, 20, 'Team has an understanding of definition of done (DOD) but it is not followed', 'OPTION'),
       (66, 20, 'The team has clear and documented set of DOD rules are follow them', 'OPTION'),
       (67, 20, 'KPIs are in place to ensure the DOD is met', 'OPTION'),
       (68, 20, 'DOD is naturaly embedded into the team process', 'OPTION'),


       (69, 21, 'Lack of transparency on requirements and current state(eg Requirement held inlarge documents)',
        'OPTION'),
       (70, 21, 'Unclear what individual resources are working on (eg bacause several backlogs of work exist)',
        'OPTION'),
       (71, 21,
        'There is one repository for requirements are tasks. Tehe backlog are work progress is visible to everybody',
        'OPTION'),
       (72, 21, 'Operation changes can be traced to budget and code', 'OPTION'),
       (73, 21, 'Adapting - there is a small backlog of user stories', 'OPTION'),

       (74, 22, 'Members only part time commtted to team. High attrition in the team', 'OPTION'),
       (75, 22, 'Teams commited but not structured by roles(eg seperated test/ dev teams)', 'OPTION'),
       (76, 22, 'Teams are setup as feature/cross functional teams', 'OPTION'),
       (77, 22, 'Teams are colocated and have autonomy as feature teams', 'OPTION'),
       (78, 22, 'team is highly flexible and roles are fungible', 'OPTION'),

       (79, 23, 'No Centralised source code management in place', 'OPTION'),
       (80, 23, 'Basec source code management practices', 'OPTION'),
       (81, 23, 'using standard git hub/changeman. Agreed branching /Review approach in place', 'OPTION'),
       (82, 23, 'Advance braching strategy in place. All changes are peer review', 'OPTION'),
       (83, 23, 'Team is expect in source control topics and supports other teams', 'OPTION'),

       (84, 24, 'Team does not have a build infrastructure shared by all developers', 'OPTION'),
       (85, 24, 'Team has a defined infrastructure to build software', 'OPTION'),
       (86, 24, 'Team use one of the UBS standard build infrastructures', 'OPTION'),
       (87, 24, 'Team runs all the builds using the standardization build chain', 'OPTION'),
       (88, 24, 'Team fully leverages the centralised build servers and acts as  SMEs', 'OPTION'),

       (89, 25, 'Team does not use central repository to store sources', 'OPTION'),
       (90, 25, 'Team does use a version control system to manage sources', 'OPTION'),
       (91, 25, 'Team is using the shared NEXUS Repository', 'OPTION'),
       (92, 25, 'The team applies standards for the repository structure', 'OPTION'),
       (110, 25, 'Team fully leverages the shared NEXUS Repository', 'OPTION'),

       (93, 26, 'No or hardly any test automation in place Amount of Automation < 20%', 'OPTION'),
       (94, 26, 'Some test automation in place Amount of Automation < 40%', 'OPTION'),
       (95, 26, 'New test are added to regression test set Amount of Automation < 60%', 'OPTION'),
       (96, 26, 'Regression test set is part of Continuous Integration Amount of Automation < 80%', 'OPTION'),
       (97, 26, 'Due to the test automation coverage no manual test are needed', 'OPTION'),

       (98, 27, 'No awareness of technical debt or the need to resolve it', 'OPTION'),
       (99, 27, 'Some awareness of technical debt but no strategy exists', 'OPTION'),
       (100, 27, 'Team actively tracks/ manages  its technical debt', 'OPTION'),
       (101, 27, 'Team capacity is put aside in every iteration to reduce  technical debt', 'OPTION'),
       (102, 27, 'Team consistly plans and works towards their Quality targets', 'OPTION'),

       (103, 28, 'Continuous Integration is not implemented', 'OPTION'),
       (104, 28, 'code is Continuously Integration at least weekly', 'OPTION'),
       (105, 28, 'Continuous Integration occurs daily to an early test environment', 'OPTION'),
       (106, 28, 'Continuous Integration occurs daily to a system test environment', 'OPTION'),
       (107, 28, 'Build breaks/bugs are fixed immedialy', 'OPTION');

select setval('categories_master_categories_master_id_seq', max(categories_master_id)) from categories_master;
