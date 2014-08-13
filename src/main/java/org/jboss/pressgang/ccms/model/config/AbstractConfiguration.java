/*
  Copyright 2011-2014 Red Hat, Inc

  This file is part of PressGang CCMS.

  PressGang CCMS is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  PressGang CCMS is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with PressGang CCMS.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.jboss.pressgang.ccms.model.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public abstract class AbstractConfiguration {
    private PropertiesConfiguration configuration = new PropertiesConfiguration();

    protected AbstractConfiguration() {
        configuration.getLayout().setForceSingleLine(true);
    }

    protected PropertiesConfiguration getConfiguration() {
        return configuration;
    }

    public void load(final File file) throws ConfigurationException {
        configuration.load(file);
        configuration.setBasePath(file.getParent());
        configuration.setFileName(file.getName());
    }

    public void save() throws ConfigurationException {
        configuration.save();
    }

    public Object getValue(final String key) {
        return configuration.getProperty(key);
    }

    public boolean containsKey(final String key) {
        return configuration.containsKey(key);
    }

    public void removeProperty(final String key) {
        getConfiguration().clearProperty(key);
    }

    public List<String> getKeys() {
        final List<String> keys = new ArrayList<String>();
        final Iterator<String> it = configuration.getKeys();
        while (it.hasNext()) {
            keys.add(it.next());
        }
        return keys;
    }

    protected String getPressGangConfigurationDirectory() {
        final String pressGangConfigurationDir = System.getProperties().getProperty("pressgang.config.dir");
        final String configurationDir;
        if (pressGangConfigurationDir == null) {
            // If the pressgang config directory isn't set then try and load from the jboss config directory
            final String jbossConfigurationDir = System.getProperties().getProperty("jboss.server.config.dir");
            if (jbossConfigurationDir == null) {
                // We aren't running on a jboss install, so just use the current working directory
                configurationDir = System.getProperty("user.dir");
            } else {
                configurationDir = jbossConfigurationDir + File.separator + "pressgang";
            }
        } else {
            configurationDir = pressGangConfigurationDir;
        }
        return configurationDir.endsWith(File.separator) ? configurationDir : (configurationDir + File.separator);
    }
}
