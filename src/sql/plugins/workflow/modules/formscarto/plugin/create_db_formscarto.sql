
--
-- Structure for table workflow_task_formscarto_config
--

DROP TABLE IF EXISTS workflow_task_formscarto_config;
CREATE TABLE workflow_task_formscarto_config (
id_task int,
id_form INT NULL,
id_step INT NULL,
id_question_list_value_closed INT NULL,
id_question_list_layer_carto INT NULL,
id_question_unit_tree INT NULL,
PRIMARY KEY (id_task)
);


DROP TABLE IF EXISTS tacheformscarto_edit_forms_carto_unit_tree;
CREATE TABLE tacheformscarto_edit_forms_carto_unit_tree (
id_edit_forms_carto_unit_tree int AUTO_INCREMENT,
id_config int NOT NULL,
field_vakue_forms varchar(255) default '' NOT NULL,
field_carto_layer varchar(255) default '' NOT NULL,
field_unittree varchar(255) default '' NOT NULL,
PRIMARY KEY (id_edit_forms_carto_unit_tree)
);